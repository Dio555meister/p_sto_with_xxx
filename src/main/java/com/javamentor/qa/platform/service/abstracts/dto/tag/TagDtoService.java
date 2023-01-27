package com.javamentor.qa.platform.service.abstracts.dto.tag;

import com.javamentor.qa.platform.models.dto.tag.TagDto;

import java.util.List;
import java.util.Optional;

public interface TagDtoService {
    Optional<List<TagDto>> getTagDtoById(Long id);
}
