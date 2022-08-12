package com.solutions.assessment.interview.klasha.service.impl;

import com.solutions.assessment.interview.klasha.domain.builder.LocationBuilder;
import com.solutions.assessment.interview.klasha.domain.builder.GenericPagedResponseDtoBuilder;
import com.solutions.assessment.interview.klasha.domain.builder.LocationConnectBuilder;
import com.solutions.assessment.interview.klasha.domain.builder.LocationDtoBuilder;
import com.solutions.assessment.interview.klasha.domain.dto.GenericPagedResponseDto;
import com.solutions.assessment.interview.klasha.domain.dto.location.AddLocationDto;
import com.solutions.assessment.interview.klasha.domain.dto.location.AddLocationResponseDto;
import com.solutions.assessment.interview.klasha.domain.dto.location.LocationDto;
import com.solutions.assessment.interview.klasha.domain.dto.location.UpdateLocationDto;
import com.solutions.assessment.interview.klasha.domain.entity.Location;
import com.solutions.assessment.interview.klasha.domain.entity.LocationConnect;
import com.solutions.assessment.interview.klasha.domain.exception.ResourceCreationException;
import com.solutions.assessment.interview.klasha.domain.exception.ResourceNotFoundException;
import com.solutions.assessment.interview.klasha.repository.LocationConnectRepository;
import com.solutions.assessment.interview.klasha.repository.LocationRepository;
import com.solutions.assessment.interview.klasha.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final LocationConnectRepository locationConnectRepository;

    @Autowired
    public LocationServiceImpl(final LocationRepository locationRepository,
                               final LocationConnectRepository locationConnectRepository) {
        this.locationRepository = locationRepository;
        this.locationConnectRepository = locationConnectRepository;
    }

    @Override
    public LocationDto getLocation(final Long id) {
        final Location location = locationRepository.findByIdAndDeletedAtIsNull(id);

        if (location == null) {
            throw new ResourceNotFoundException("Location not found");
        }

        return new LocationDtoBuilder()
                .setId(location.getId())
                .setLatitude(location.getLatitude())
                .setLongitude(location.getLongitude())
                .setName(location.getName())
                .setConnectedWithLocationList(resolveConnectedWithLocations(
                        location.getConnectedWithLocationList()))
                .build();
    }

    @Override
    public AddLocationResponseDto addLocation(final AddLocationDto addLocationDto) {

        final Location existingLocation = locationRepository
                .findByLongitudeAndLatitudeAndDeletedAtIsNull(addLocationDto.getLongitude(),
                        addLocationDto.getLatitude());

        if (existingLocation != null) {
            throw new ResourceCreationException("Location with same coordinates exist");
        }

        Location location = new LocationBuilder()
                .setLongitude(addLocationDto.getLongitude())
                .setLatitude(addLocationDto.getLatitude())
                .setName(addLocationDto.getName())
                .setCreatedAt(LocalDateTime.now())
                .build();

        location = locationRepository.save(location);

        return new AddLocationResponseDto(location.getId());
    }

    @Override
    public GenericPagedResponseDto<LocationDto> retrieveLocations(Integer pageNumber, Integer pageSize) {

        pageNumber = pageNumber == null ? 0 : pageNumber;
        pageSize = pageSize == null ? 10 : pageSize;

        final Pageable page = PageRequest.of(pageNumber, pageSize);

        final Page<Location> locationPage = locationRepository.findByDeletedAtIsNull(page);

        final List<LocationDto> locationDtoList = locationPage.toList()
                .stream()
                .map(location -> new LocationDtoBuilder()
                        .setId(location.getId())
                        .setName(location.getName())
                        .setLatitude(location.getLatitude())
                        .setLongitude(location.getLongitude())
                        .setConnectedWithLocationList(resolveConnectedWithLocations(
                                location.getConnectedWithLocationList()))
                        .build())
                .collect(Collectors.toList());

        return new GenericPagedResponseDtoBuilder<LocationDto>()
                .setData(locationDtoList)
                .setHasNext(locationPage.hasNext())
                .setHasPrevious(locationPage.hasPrevious())
                .setPageSize(locationPage.getSize())
                .setPageNumber(locationPage.getNumber())
                .setTotPages(locationPage.getTotalPages())
                .setTotElements(locationPage.getTotalElements())
                .build();
    }

    @Transactional
    @Override
    public LocationDto updateLocation(final UpdateLocationDto updateLocationDto) {
        Location location = locationRepository.findByIdAndDeletedAtIsNull(updateLocationDto.getId());

        if (location == null) {
            throw new ResourceNotFoundException("Could not find location");
        }

        location.setName(updateLocationDto.getUpdatedName() != null ?
                updateLocationDto.getUpdatedName() : location.getName());

        location.setLatitude(updateLocationDto.getUpdatedLatitude() != null ?
                updateLocationDto.getUpdatedLatitude() : location.getLatitude());

        location.setLongitude(updateLocationDto.getUpdatedLongitude() != null ?
                updateLocationDto.getUpdatedLongitude() : location.getLatitude());

        for (LocationConnect lConnect : location.getConnectedWithLocationList()) {
            final List<Long> removedConnectedLocationList = updateLocationDto.getRemovedConnectedLocationList();

            if (removedConnectedLocationList.contains(lConnect.getLocationA().getId()) ||
                    removedConnectedLocationList.contains(lConnect.getLocationB().getId())) {
                lConnect.setDeletedAt(LocalDateTime.now());
            }
        }

        location = locationRepository.save(location);

        final List<Long> addedConnectedLocationList = new ArrayList<>(updateLocationDto
                .getAddedConnectedLocationList());

        if (location.getConnectedWithLocationList().isEmpty()) {
            addedConnectedLocationList.add(location.getId());
        }

        final List<Location> locationList = locationRepository
                .findByIdInAndDeletedAtIsNull(addedConnectedLocationList);

        final Location finalLocation = location;
        List<LocationConnect> locationConnectList = locationList
                .stream()
                .filter(locationCheck -> !locationCheck.getId().equals(finalLocation.getId()))
                .map(lConnectB -> new LocationConnectBuilder()
                        .setLocationA(finalLocation)
                        .setLocationB(lConnectB)
                        .setCreatedAt(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        if (!locationConnectList.isEmpty()) {
            locationConnectList = locationConnectRepository.saveAll(locationConnectList);

            return buildLocationDto(locationConnectList.get(0).getLocationA());
        }

        return buildLocationDto(location);
    }

    private LocationDto buildLocationDto(final Location location) {
        return new LocationDtoBuilder()
                .setId(location.getId())
                .setName(location.getName())
                .setLongitude(location.getLongitude())
                .setLatitude(location.getLatitude())
                .setConnectedWithLocationList(resolveConnectedWithLocations(
                        location.getConnectedWithLocationList()))
                .build();
    }

    private List<LocationDto> resolveConnectedWithLocations(final List<LocationConnect> connectedWithLocationList) {

        final List<Location> locationList = new ArrayList<>();

        for (LocationConnect conLocation : connectedWithLocationList) {
            locationList.add(conLocation.getLocationA());
            locationList.add(conLocation.getLocationB());
        }

        return locationList.stream()
                .map(location -> new LocationDtoBuilder()
                        .setId(location.getId())
                        .setName(location.getName())
                        .setLongitude(location.getLongitude())
                        .setLatitude(location.getLatitude())
                        .setConnectedWithLocationList(
                                resolveConnectedWithLocations(location.getConnectedWithLocationList()))
                        .build())
                .collect(Collectors.toList());
    }
}
