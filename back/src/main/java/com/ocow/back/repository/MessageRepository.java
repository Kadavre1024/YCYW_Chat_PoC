package com.ocow.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ocow.back.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findAllByDiscussionId(Long id);

}
