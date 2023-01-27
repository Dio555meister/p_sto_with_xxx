package com.javamentor.qa.platform.dao.abstracts.dto.tag;

import com.javamentor.qa.platform.models.dto.tag.TagDto;

import java.util.List;
import java.util.Optional;

public interface TagDtoDao {
    Optional<List<TagDto>> getTagDtoById(Long id);
}
