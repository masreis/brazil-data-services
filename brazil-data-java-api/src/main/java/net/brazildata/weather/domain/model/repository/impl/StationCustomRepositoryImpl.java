package net.brazildata.weather.domain.model.repository.impl;

import javax.persistence.EntityManager;

import net.brazildata.weather.domain.model.repository.StationCustomRepository;

public class StationCustomRepositoryImpl implements StationCustomRepository {

  private EntityManager em;

  public StationCustomRepositoryImpl(EntityManager em) {
    this.em = em;
  }
}
