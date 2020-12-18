package net.brazildata.weather.repository.impl;

import javax.persistence.EntityManager;

import net.brazildata.weather.repository.StationCustomRepository;

public class StationCustomRepositoryImpl implements StationCustomRepository {

  private EntityManager em;

  public StationCustomRepositoryImpl(EntityManager em) {
    this.em = em;
  }
}
