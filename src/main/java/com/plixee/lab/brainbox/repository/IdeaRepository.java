package com.plixee.lab.brainbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plixee.lab.brainbox.model.Idea;

public interface IdeaRepository extends JpaRepository<Idea, Long> {
}
