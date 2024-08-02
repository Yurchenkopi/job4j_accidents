package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHbm implements AccidentStore {
    private final SessionFactory sf;

    private static final Logger LOG = LoggerFactory.getLogger(AccidentHbm.class.getName());

    @Override
    public Collection<Accident> findAll() {
        Session session = sf.openSession();
        Collection<Accident> rsl = Collections.emptyList();
        try {
            session.beginTransaction();
            String sql = """
                    SELECT DISTINCT a
                    FROM Accident a
                    JOIN FETCH a.type
                    JOIN FETCH a.rules
                    ORDER BY a.id ASC
                    """;
            var query = session.createQuery(sql, Accident.class);
            session.getTransaction().commit();
            rsl = query.list();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Возникло исключение при поиске всех записей в БД.");
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Accident create(Accident accident) {
        Session session = sf.openSession();
        Accident rsl = null;
        try {
            session.beginTransaction();
            session.save(accident);
            session.getTransaction().commit();
            rsl = accident;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Объект accident не был сохранен в БД по причине возникновения исключения.");
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public boolean update(Accident accident) {
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            rsl = session.merge(accident) != null;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Не удалось обновить accident в БД по причине возникновения исключения.");
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Optional<Accident> findById(Integer accidentId) {
        Session session = sf.openSession();
        Optional<Accident> rsl = Optional.empty();
        try {
            session.beginTransaction();
            String sql = """
                    SELECT DISTINCT a
                    FROM Accident a
                    JOIN FETCH a.type
                    JOIN FETCH a.rules
                    WHERE a.id = :fId
                    ORDER BY a.id ASC
                    """;
            Query<Accident> query = session.createQuery(sql, Accident.class);
            query.setParameter("fId", accidentId);
            session.getTransaction().commit();
            rsl = query.uniqueResultOptional();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Возникло исключение при поиске записи в БД по ID инцидента.");
        } finally {
            session.close();
        }
        return rsl;
    }

}
