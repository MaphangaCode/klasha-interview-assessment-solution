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
import java.util.*;
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

        final List<LocationConnect> locationConnectList = locationConnectRepository
                .findAllBySourceLocationIdOrDestLocationIdAndDeletedAtIsNull(id, id);

        final List<Location> connectedWithLocationList = new ArrayList<>();

        for (final LocationConnect lConnect : locationConnectList) {
            if (!location.getId().equals(lConnect.getSourceLocation().getId())) {
                connectedWithLocationList.add(lConnect.getSourceLocation());
            }

            if (!location.getId().equals(lConnect.getDestLocation().getId())) {
                connectedWithLocationList.add(lConnect.getDestLocation());
            }
        }

        return new LocationDtoBuilder()
                .setId(location.getId())
                .setLatitude(location.getLatitude())
                .setLongitude(location.getLongitude())
                .setName(location.getName())
                .setConnectedWithLocationList(connectedWithLocationList.stream()
                        .map(locationCount -> new LocationDtoBuilder()
                                .setId(locationCount.getId())
                                .setName(locationCount.getName())
                                .setLatitude(locationCount.getLatitude())
                                .setLongitude(locationCount.getLongitude())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
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

        final Page<LocationConnect> locationConnectListPage = locationConnectRepository
                .findAllBySourceLocationDeletedAtIsNullOrDestLocationDeletedAtIsNullAndDeletedAtIsNull(page);

        if (!locationConnectListPage.toList().isEmpty()) {

            return getGenericPagedResponseDto(retrieveMapByLocationConnect(locationConnectListPage.toList()),
                    locationConnectListPage.hasNext(),
                    locationConnectListPage.hasPrevious(),
                    locationConnectListPage.getNumber(),
                    locationConnectListPage.getSize(),
                    locationConnectListPage.getTotalPages(),
                    locationConnectListPage.getTotalElements());
        } else {
            final Page<Location> locationPage = locationRepository.findByDeletedAtIsNull(page);

            return getGenericPagedResponseDto(retrieveMapByLocation(locationPage.toList()),
                    locationPage.hasNext(),
                    locationPage.hasPrevious(),
                    locationPage.getNumber(),
                    locationPage.getSize(),
                    locationPage.getTotalPages(),
                    locationPage.getTotalElements());
        }

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

        location = locationRepository.save(location);

        final List<LocationConnect> locationConnectList = locationConnectRepository
                .findAllBySourceLocationIdOrDestLocationIdAndDeletedAtIsNull(location.getId(),
                        location.getId());

        return new LocationDtoBuilder()
                .setId(location.getId())
                .setName(location.getName())
                .setLongitude(location.getLongitude())
                .setLatitude(location.getLatitude())
                .setConnectedWithLocationList(locationConnectList.stream()
                        .map(locationConnect -> new LocationDtoBuilder()
                                .setId(locationConnect.getDestLocation().getId())
                                .setName(locationConnect.getDestLocation().getName())
                                .setLatitude(locationConnect.getDestLocation().getLatitude())
                                .setLongitude(locationConnect.getDestLocation().getLongitude())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public List<LocationConnect> getShortPathSourceAndDestLocationConnect(final Long sourceLocationId,
                                                                          final Long destLocationId) {
       return locationConnectRepository
               .findAllBySourceLocationIdOrDestLocationIdAndDeletedAtIsNull(sourceLocationId, destLocationId);
    }

    @Transactional
    @Override
    public LocationDto connectLocation(final Long originLocationId,
                                       final List<Long> destinationIdList) {
        final List<Long> locationIdList = new ArrayList<>();
        locationIdList.add(originLocationId);
        locationIdList.addAll(destinationIdList);

        final List<Location> locationList = locationRepository
                .findAllByIdInAndDeletedAtIsNull(locationIdList);

        final Location sourceLocation = locationList.stream()
                .filter(location -> originLocationId.equals(location.getId()))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Could not find origin location"));

        final List<LocationConnect> locationConnectList = new ArrayList<>();

        for (Location location : locationList) {

            if (!location.getId().equals(sourceLocation.getId())) {
                if (!locationIdList.contains(location.getId())) {
                    throw new ResourceNotFoundException("Could not find location");
                }

                final LocationConnect locationConnect = new LocationConnectBuilder()
                        .setLocationA(sourceLocation)
                        .setLocationB(location)
                        .setCreatedAt(LocalDateTime.now())
                        .build();

                locationConnectList.add(locationConnect);
            }
        }

        locationConnectRepository.saveAll(locationConnectList);

        return new LocationDtoBuilder()
                .setId(sourceLocation.getId())
                .setLongitude(sourceLocation.getLongitude())
                .setLatitude(sourceLocation.getLatitude())
                .setName(sourceLocation.getName())
                .setConnectedWithLocationList(locationList.stream()
                        .filter(location -> !sourceLocation.getId().equals(location.getId()))
                        .findAny()
                        .map(location -> new LocationDtoBuilder()
                                .setId(location.getId())
                                .setName(location.getName())
                                .setLatitude(location.getLatitude())
                                .setLongitude(location.getLongitude())
                                .build())
                        .stream()
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    @Override
    public void removeLocation(final Long locationId) {
        final Location location = locationRepository.findByIdAndDeletedAtIsNull(locationId);

        if (location == null) {
            throw new ResourceNotFoundException("Could not find location with specified ID");
        }

        location.setDeletedAt(LocalDateTime.now());
        locationRepository.save(location);

        final List<LocationConnect> locationConnectList = locationConnectRepository
                .findAllBySourceLocationIdOrDestLocationIdAndDeletedAtIsNull(locationId, locationId);

        for (LocationConnect locationConnect : locationConnectList) {
            locationConnect.setDeletedAt(LocalDateTime.now());
        }

        locationConnectRepository.saveAll(locationConnectList);
    }

    @Override
    public Integer getLocationTotalCount() {
        return locationRepository.findByDeletedAtIsNull().size();
    }

    private Map<Location, List<Location>> retrieveMapByLocation(final List<Location> locationList) {
        final Map<Location, List<Location>> locationListMap = new HashMap<>();

        for (Location location: locationList) {
            locationListMap.put(location, new ArrayList<>());
        }

        return locationListMap;
    }

    private Map<Location, List<Location>> retrieveMapByLocationConnect(final List<LocationConnect> locationConnectList) {
        final Map<Location, List<Location>> locationListMap = new HashMap<>();

        for (LocationConnect lConnect: locationConnectList) {
            if (!locationListMap.containsKey(lConnect.getSourceLocation())) {
                final List<Location> connectedWithList = new ArrayList<>();
                connectedWithList.add(lConnect.getDestLocation());
                locationListMap.put(lConnect.getSourceLocation(), connectedWithList);
            }

            if (!locationListMap.containsKey(lConnect.getDestLocation())) {
                final List<Location> connectedWithList = new ArrayList<>();
                connectedWithList.add(lConnect.getSourceLocation());
                locationListMap.put(lConnect.getDestLocation(), connectedWithList);
            }

            if (locationListMap.containsKey(lConnect.getSourceLocation())) {
                if (!locationListMap.get(lConnect.getSourceLocation()).contains(lConnect.getDestLocation())) {
                    locationListMap.get(lConnect.getSourceLocation()).add(lConnect.getDestLocation());
                }
            }

            if (locationListMap.containsKey(lConnect.getDestLocation())) {
                if (!locationListMap.get(lConnect.getDestLocation()).contains(lConnect.getSourceLocation())) {
                    locationListMap.get(lConnect.getDestLocation()).add(lConnect.getSourceLocation());
                }
            }
        }

        return locationListMap;
    }

    private GenericPagedResponseDto<LocationDto> getGenericPagedResponseDto(final Map<Location, List<Location>> locationListMap,
                                                                            final Boolean hasNext,
                                                                            final Boolean hasPrevious,
                                                                            final Integer pageNumber,
                                                                            final Integer pageSize,
                                                                            final Integer totPages,
                                                                            final Long totElements) {

        final List<LocationDto> locationDtoList = locationListMap.entrySet().stream()
                .map(locationListEntry -> {
                    final List<LocationDto> connectedWithLocationDtoList = locationListEntry.getValue().stream()
                            .map(locationTemp -> new LocationDtoBuilder()
                                    .setId(locationTemp.getId())
                                    .setName(locationTemp.getName())
                                    .setLongitude(locationTemp.getLongitude())
                                    .setLatitude(locationTemp.getLatitude())
                                    .build())
                            .collect(Collectors.toList());

                    final Location location = locationListEntry.getKey();
                    return new LocationDtoBuilder()
                            .setId(location.getId())
                            .setName(location.getName())
                            .setLatitude(location.getLatitude())
                            .setLongitude(location.getLongitude())
                            .setConnectedWithLocationList(connectedWithLocationDtoList)
                            .build();
                })
                .collect(Collectors.toList());

        return new GenericPagedResponseDtoBuilder<LocationDto>()
                .setData(locationDtoList)
                .setHasNext(hasNext)
                .setHasPrevious(hasPrevious)
                .setPageSize(pageSize)
                .setPageNumber(pageNumber)
                .setTotPages(totPages)
                .setTotElements(totElements)
                .build();
    }
}
