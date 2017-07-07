package com.github.stoton.repository.impl;

import com.github.stoton.domain.Manga;
import com.github.stoton.repository.MangaRepository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class MangaRepositoryImpl implements MangaRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveOrUpdate(Manga manga) {
        getSession().save(manga);
    }

    @Override
    public Manga find(long id) {
        return getSession().get(Manga.class, (long) id);
    }

    @Override
    public List<Manga> getAllManga() {
        return getSession().createCriteria(Manga.class).list();
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
