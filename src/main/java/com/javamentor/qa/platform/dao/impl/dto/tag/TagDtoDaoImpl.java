package com.javamentor.qa.platform.dao.impl.dto.tag;

import com.javamentor.qa.platform.dao.abstracts.dto.tag.TagDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;


@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TagDto> getTagDtoById(Long id) {
        Query query = entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.tag.TagDto(" +
                        "t.id, " +
                        "t.name, " +
                        "t.description) " +
                        "from Tag t join t.questions as tq where tq.id = :id")
                .setParameter("id", id);
        return (List<TagDto>) query.getResultList();
    }

    @Override
    public List<RelatedTagsDto> getRelatedTagsDto() {
        Query query = entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto(" +
                "t.id as id, " +
                "t.name as title, " +
                "(select count (q.id) from Question q join q.tags qt where t.id = qt.id) as countQuestion) " +
                "from Tag t order by countQuestion desc ").setMaxResults(10);
        return (List<RelatedTagsDto>) query.getResultList();
    }

    @Override
    public Optional<TagDto> getById(Long id) {
        Query query = entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.tag.TagDto(" +
                        "t.id, " +
                        "t.name, " +
                        "t.description) " +
                        "from Tag t where t.id = :id")
                .setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public List<TagDto> getIgnoredTagByUserId(Long id) {
        Query query = entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.tag.TagDto(" +
                        "t.id, " +
                        "t.name) " +
                        "from Tag t join IgnoredTag it on t.id = it.ignoredTag.id where it.user.id = :id")
                .setParameter("id", id);
        return (List<TagDto>) query.getResultList();
    }

    @Override
    public Map<Long, List<TagDto>> getTagsMapByQuestionId(List<Long> qId) {
        List<Tuple> tags = entityManager.createQuery(
                "select t.id as tag_id, " +
                        "t.name as tag_name, " +
                        "t.description as tag_description, " +
                        "q.id as question_id " +
                        "from Tag t join t.questions q where q.id in :id", Tuple.class)
                .setParameter("id", qId)
                .getResultList();
        Map<Long, List<TagDto>> tagsMap = new HashMap<>();

        tags.forEach(tupleList -> tagsMap.computeIfAbsent((Long) tupleList.get("question_id"), key -> new ArrayList<>())
                .add(new TagDto((Long) tupleList.get("tag_id"),
                        (String) tupleList.get("tag_name"),
                        (String) tupleList.get("tag_description"))));
        return tagsMap;
    }

    @Override
    public List<TagDto> getTrackedTagsByUserId(Long userId) {
        Query query = entityManager.createQuery("SELECT new com.javamentor.qa.platform.models.dto.tag.TagDto(" +
                        "tt.trackedTag.id, " +
                        "tt.trackedTag.name " +
                        ") " +
                        "FROM TrackedTag tt WHERE tt.user.id = :id")
                .setParameter("id", userId);
        return (List<TagDto>) query.getResultList();
    }

    @Override
    public List<TagDto> getTagsByName(String tagName) {
        Query query = entityManager.createQuery("SELECT new com.javamentor.qa.platform.models.dto.tag.TagDto(" +
                        "t.id, " +
                        "t.name, " +
                        "t.description" +
                        ")" +
                        "FROM Tag t WHERE upper(t.name) like concat('%', upper(:name), '%')")
                .setParameter("name", tagName)
                .setMaxResults(6);
        return (List<TagDto>) query.getResultList();
    }


}
