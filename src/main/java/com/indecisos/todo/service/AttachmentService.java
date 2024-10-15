package com.indecisos.todo.service;

import com.indecisos.todo.model.Attachment;
import com.indecisos.todo.model.Task;
import com.indecisos.todo.repository.AttachmentRepository;
import com.indecisos.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private TaskRepository taskRepository;

    public List<Attachment> findAll() {
        return attachmentRepository.findAll();
    }

    public Optional<Attachment> findById(Long id) {
        return attachmentRepository.findById(id);
    }

    public List<Attachment> findByTaskId(Long taskId) {
        return attachmentRepository.findByTaskId(taskId);
    }

    public Attachment save(Attachment attachment, Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        attachment.setTask(task);
        return attachmentRepository.save(attachment);
    }

    public void deleteById(Long id) {
        attachmentRepository.deleteById(id);
    }

    public Attachment update(Long id, Attachment attachmentDetails) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Attachment not found"));
        attachment.setFileName(attachmentDetails.getFileName());
        attachment.setUrl(attachmentDetails.getUrl());
        attachment.setContent(attachmentDetails.getContent());
        return attachmentRepository.save(attachment);
    }
}
