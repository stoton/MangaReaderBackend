package com.github.stoton.repository.impl;

import com.github.stoton.domain.MangaPage;
import com.github.stoton.repository.MangaPageRepository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class MangaPageRepositoryImpl implements MangaPageRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(MangaPage mangaPage) {
        getSession().save(mangaPage);
    }

    @Override
    public List<MangaPage> findMangaPagesById(long mangaChapterId) {
        String sql = "select * from manga_page where mangaChapter_id = :mangaChapterId";

        return getSession().createSQLQuery(sql)
                .addEntity(MangaPage.class)
                .setParameter("mangaChapterId", mangaChapterId)
                .list();
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
