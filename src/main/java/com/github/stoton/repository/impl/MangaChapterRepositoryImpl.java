package com.github.stoton.repository.impl;

import com.github.stoton.domain.MangaChapter;
import com.github.stoton.repository.MangaChapterRepository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional
public class MangaChapterRepositoryImpl implements MangaChapterRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<MangaChapter> getMangaChapters(long manga_id) {
        String sql = "select * from manga_chapter where manga_chapter.manga_id = :manga_id";
        return getSession().createSQLQuery(sql)
                .addEntity(MangaChapter.class)
                .setParameter("manga_id", manga_id)
                .list();
    }

    @Override
    public MangaChapter getMangaChapterById(long manga_id, long chapter_id) {
        String sql = "select * from manga_chapter where manga_chapter.manga_id = :manga_id and manga_chapter.number= :chapter_id";
        return (MangaChapter) getSession().createSQLQuery(sql)
                .addEntity(MangaChapter.class)
                .setParameter("manga_id", manga_id)
                .setParameter("chapter_id", chapter_id)
                .uniqueResult();
    }

    @Override
    public int getMangaChapterLength(long id) {
        String sql = "select count(*) from manga_chapter where manga_chapter.manga_id = :id";
        BigInteger size = (BigInteger) getSession().createSQLQuery(sql)
                .setParameter("id", id)
                .uniqueResult();
        return size.intValue();
    }

    @Override
    public void saveOrUpdate(MangaChapter mangaChapter) {
        getSession().saveOrUpdate(mangaChapter);
    }

    @Override
    public List<MangaChapter> getMangaChaptersById(long mangaId) {
        String sql = "select * from manga_chapter where manga_chapter.manga_id = :mangaId";
        return  getSession().createSQLQuery(sql)
                .addEntity(MangaChapter.class)
                .setParameter("mangaId", mangaId)
                .list();
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
