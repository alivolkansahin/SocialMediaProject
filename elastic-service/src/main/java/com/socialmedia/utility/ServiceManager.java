package com.socialmedia.utility;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

//public class ServiceManager<T extends BaseEntity,ID> implements IService<T,ID>{
public class ServiceManager<T,ID> implements IService<T,ID>{

    private final ElasticsearchRepository<T,ID> elasticsearchRepository;

    public ServiceManager(ElasticsearchRepository<T, ID> jpaRepository) {
        this.elasticsearchRepository = jpaRepository;
    }

    @Override
    public T save(T t) {
//        Long time = System.currentTimeMillis();
//        t.setCreateAt(time); // T extends BaseEntity ile geldi.
//        t.setUpdateAt(time);
//        t.setState(true);
        return elasticsearchRepository.save(t);
    }

//    @Override
//    public T saveAndFlush(T t) {
////        Long time = System.currentTimeMillis();
////        t.setCreateAt(time); // T extends BaseEntity ile geldi.
////        t.setUpdateAt(time);
////        t.setState(true);
//        return jpaRepository.saveAndFlush(t);
//    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t) {
//        Long time = System.currentTimeMillis();
//        t.forEach(t1 -> {
//            t1.setCreateAt(time);
//            t1.setUpdateAt(time);
//            t1.setState(true);
//        });
        return elasticsearchRepository.saveAll(t);
    }

//    @Override
//    public Iterable<T> saveAllAndFlush(Iterable<T> t) {
////        Long time = System.currentTimeMillis();
////        t.forEach(t1 -> {
////            t1.setCreateAt(time);
////            t1.setUpdateAt(time);
////            t1.setState(true);
////        });
//        return jpaRepository.saveAllAndFlush(t);
//    }

    @Override
    public T update(T t) {
//        t.setUpdateAt(System.currentTimeMillis());
        return elasticsearchRepository.save(t); // verilen entityde id bilgisi yoksa save methodu kaydediyor, id bilgisi varsa gidiyor o idli entityi güncelliyor. böyle çalışıyor arka planda
    }

    @Override
    public void delete(T t) {
        elasticsearchRepository.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        elasticsearchRepository.deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return elasticsearchRepository.findById(id);
    }

    @Override
    public Iterable<T> findAll() {
        return elasticsearchRepository.findAll();
    }

    @Override
    public Boolean existsById(ID id) {
        return elasticsearchRepository.existsById(id);
    }
}
