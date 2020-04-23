package ro.utcn.sd.agui.a1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.agui.a1.entity.Tag;
import ro.utcn.sd.agui.a1.exception.TagNotFoundException;
import ro.utcn.sd.agui.a1.persistence.RepositoryFactory;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagManagementService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    public Tag addTag(Tag tag){
        return repositoryFactory.createTagRepository().save(tag);
    }

    @Transactional
    public Tag findTagByName(String name){

        return repositoryFactory.createTagRepository().findByName(name).orElseThrow(TagNotFoundException::new);
    }
}
