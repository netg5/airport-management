package org.sergei.manager.service;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.sergei.manager.jpa.model.FlyMode;
import org.sergei.manager.jpa.model.Price;
import org.sergei.manager.jpa.repository.FlyModeRepository;
import org.sergei.manager.rest.dto.FlyModeDTO;
import org.sergei.manager.rest.dto.mappers.FlyModeDTOListMapper;
import org.sergei.manager.rest.dto.mappers.FlyModeDTOMapper;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@Service
public class FlyModeServiceImpl implements FlyModeService {

    private final FlyModeRepository flyModeRepository;
    private final FlyModeDTOMapper flyModeDTOMapper;
    private final FlyModeDTOListMapper flyModeDTOListMapper;
    private final MessageService messageService;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public FlyModeServiceImpl(FlyModeRepository flyModeRepository,
                              FlyModeDTOMapper flyModeDTOMapper,
                              FlyModeDTOListMapper flyModeDTOListMapper, MessageService messageService) {
        this.flyModeRepository = flyModeRepository;
        this.flyModeDTOMapper = flyModeDTOMapper;
        this.flyModeDTOListMapper = flyModeDTOListMapper;
        this.messageService = messageService;
    }

    @Override
    public ResponseEntity<ResponseDTO<FlyModeDTO>> findAllFlyModes() {
        List<FlyMode> flyModes = flyModeRepository.findAll();
        List<FlyModeDTO> flyModeDTO = flyModeDTOListMapper.apply(flyModes);

        ResponseDTO<FlyModeDTO> response = new ResponseDTO<>();
        response.setErrorList(ImmutableList.of());
        response.setResponse(flyModeDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDTO<FlyModeDTO>> findFlyModeByCode(String code) {
        if (code != null) {
            Optional<FlyMode> flyMode = flyModeRepository.findFlyModeByCode(code);
            if (flyMode.isPresent()) {
                FlyModeDTO flyModeDTO = flyModeDTOMapper.apply(flyMode.get());
                ResponseDTO<FlyModeDTO> response = new ResponseDTO<>();
                response.setErrorList(ImmutableList.of());
                response.setResponse(ImmutableList.of(flyModeDTO));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                log.debug("Fly mode with this code: {} not found", code);
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("FLY-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
            }
        } else {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<FlyModeDTO>> findFlyModeByCodeAndCurrency(String code, String currency) {
        if (code != null || currency != null) {
            Query query = em.createNativeQuery(
                    "SELECT " +
                            "   fm.code as fm_code, " +
                            "   fm.title as fm_title, " +
                            "   fm.description as fm_descr, " +
                            "   pr.code AS pr_code, " +
                            "   pr.amount as pr_amount, " +
                            "   pr.currency as pr_currency " +
                            " FROM fly_modes fm " +
                            " LEFT JOIN fly_modes_prices_relation fmpr " +
                            "     ON fm.code = fmpr.fly_modes_code " +
                            " LEFT JOIN prices pr " +
                            "     ON pr.code = fmpr.prices_code " +
                            " WHERE fm.code = :code AND pr.currency = :currency");
            query.setParameter("code", code);
            query.setParameter("currency", currency);
            FlyMode flyMode = FlyMode.builder()
                    .code(query.getParameterValue("fm_code").toString())
                    .title(query.getParameterValue("fm_title").toString())
                    .description(query.getParameterValue("fm_description").toString())
                    .price(ImmutableList.of(
                            Price.builder()
                                    .code(query.getParameterValue("pr_code").toString())
                                    .amount(Double.valueOf(query.getParameterValue("pr_amount").toString()))
                                    .currency(query.getParameterValue("pr_currency").toString())
                                    .build()
                    ))
                    .build();
            if (flyMode != null) {
                FlyModeDTO flyModeDTO = flyModeDTOMapper.apply(flyMode);
                ResponseDTO<FlyModeDTO> response = new ResponseDTO<>();
                response.setErrorList(ImmutableList.of());
                response.setResponse(ImmutableList.of(flyModeDTO));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                log.debug("Fly mode with this code: {} not found", code);
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("FLY-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
            }
        } else {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        }
    }
}
