package me.pedrazas.dropangular;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import me.pedrazas.dropangular.om.Ping;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;

public class PingDAO extends AbstractDAO<Ping>{

	public PingDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Ping> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public Ping create(Ping ping) {
        return persist(ping);
    }

    public List<Ping> findAll() {
        return list(namedQuery("me.pedrazas.dropangular.om.Ping.findAll"));
    }
}
