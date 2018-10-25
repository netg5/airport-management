package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.RouteDAO;
import org.sergei.flightreservation.dto.RouteDTO;
import org.sergei.flightreservation.model.Route;
import org.sergei.flightreservation.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService implements IService<RouteDTO> {

    private final ModelMapper modelMapper;
    private final RouteDAO routeDAO;

    @Autowired
    public RouteService(ModelMapper modelMapper, RouteDAO routeDAO) {
        this.modelMapper = modelMapper;
        this.routeDAO = routeDAO;
    }

    @Override
    public RouteDTO findOne(Long routeId) {
        Route route = routeDAO.findOne(routeId);
        return modelMapper.map(route, RouteDTO.class);
    }

    @Override
    public List<RouteDTO> findAll() {
        List<Route> routeList = routeDAO.findAll();
        return ObjectMapperUtils.mapAll(routeList, RouteDTO.class);
    }

    @Override
    public RouteDTO save(RouteDTO entity) {
        // TODO
        return null;
    }

    @Override
    public RouteDTO update(Long aLong, RouteDTO entity) {
        // TODO
        return null;
    }

    @Override
    public RouteDTO delete(Long aLong) {
        // TODO
        return null;
    }
}
