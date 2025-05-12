package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repositories.AvatarRepository;

import java.util.logging.Logger;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;

    Logger logger = Logger.getLogger(AvatarService.class.getName());

    @Autowired
    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Page<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        logger.info( "Was invoked method for get avatars" );
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return avatarRepository.findAll(pageRequest);
    }
}