package ro.utcn.sd.agui.a1.persistence.memory;

import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.Tag;
import ro.utcn.sd.agui.a1.persistence.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryTagRepository implements TagRepository {

    private final Map<Integer, Tag> data = new ConcurrentHashMap<>();
    private final AtomicInteger current = new AtomicInteger(0);

    @Override
    public Tag save(Tag tag) {
        if(tag.getTagId()==null){
            tag.setTagId(current.incrementAndGet());
        }
        data.put(tag.getTagId(), tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(Tag tag) {
        data.remove(tag.getTagId());
    }

    @Override
    public List<Tag> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void addTagToQuestion(Tag tag, Question question){
        List<Tag> oldTags = question.getTags();
        oldTags.add(tag);
        question.setTags(oldTags);
    }

    @Override
    public Optional<Tag> findByName(String name){

        Tag foundTag = null;
        List<Tag> tags = new ArrayList<>(data.values());
        for (Tag tagIterator: tags){
            if(tagIterator.getName().equals(name)){
                foundTag = tagIterator;
                break;
            }
        }

        return foundTag == null ? Optional.empty() : Optional.of(foundTag);
    }

}
