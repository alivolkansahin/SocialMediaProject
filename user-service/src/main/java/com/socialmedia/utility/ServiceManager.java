package com.socialmedia.utility;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

//public class ServiceManager<T extends BaseEntity,ID> implements IService<T,ID>{
public class ServiceManager<T,ID> implements IService<T,ID>{

    private final MongoRepository<T,ID> mongoRepository;

    public ServiceManager(MongoRepository<T, ID> jpaRepository) {
        this.mongoRepository = jpaRepository;
    }

    @Override
    public T save(T t) {
//        Long time = System.currentTimeMillis();
//        t.setCreateAt(time); // T extends BaseEntity ile geldi.
//        t.setUpdateAt(time);
//        t.setState(true);
        return mongoRepository.save(t);
    }

//    @Override
//    public T saveAndFlush(T t) {
////        Long time = System.currentTimeMillis();
////        t.setCreateAt(time); // T extends BaseEntity ile geldi.
////        t.setUpdateAt(time);
////        t.setState(true);
//        return mongoRepository.saveAndFlush(t);
//    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t) {
//        Long time = System.currentTimeMillis();
//        t.forEach(t1 -> {
//            t1.setCreateAt(time);
//            t1.setUpdateAt(time);
//            t1.setState(true);
//        });
        return mongoRepository.saveAll(t);
    }

//    @Override
//    public Iterable<T> saveAllAndFlush(Iterable<T> t) {
////        Long time = System.currentTimeMillis();
////        t.forEach(t1 -> {
////            t1.setCreateAt(time);
////            t1.setUpdateAt(time);
////            t1.setState(true);
////        });
//        return mongoRepository.saveAllAndFlush(t);
//    }

    @Override
    public T update(T t) {
//        t.setUpdateAt(System.currentTimeMillis());
        return mongoRepository.save(t); // verilen entityde id bilgisi yoksa save methodu kaydediyor, id bilgisi varsa gidiyor o idli entityi güncelliyor. böyle çalışıyor arka planda
    }

    @Override
    public void delete(T t) {
        mongoRepository.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        mongoRepository.deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return mongoRepository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return mongoRepository.findAll();
    }

    @Override
    public Boolean existsById(ID id) {
        return mongoRepository.existsById(id);
    }
}
