package ru.skypro.lessons.springboot.weblibrary.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.dto.CreatePosition;
import ru.skypro.lessons.springboot.weblibrary.repository.PositionRepository;

@Service
public class PositionServiceImpl implements PositionService{

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionServiceImpl.class);

    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public CreatePosition addPosition(CreatePosition createPosition) {
        positionRepository.save(createPosition.toPosition());
        LOGGER.info("Position was added: {}", createPosition);
        LOGGER.debug("Database was updated");
        return createPosition;
    }
}
