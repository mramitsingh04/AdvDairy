package com.generic.khatabook.notebook.service.impl;

import com.generic.khatabook.notebook.entity.GenerationDate;
import com.generic.khatabook.notebook.entity.Notebook;
import com.generic.khatabook.notebook.exceptions.AppEntity;
import com.generic.khatabook.notebook.exceptions.NotFoundException;
import com.generic.khatabook.notebook.model.NotebookDTO;
import com.generic.khatabook.notebook.repository.NotebookRepository;
import com.generic.khatabook.notebook.service.NotebookService;
import com.generic.khatabook.notebook.service.mapper.NotebookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotebookServiceImpl implements NotebookService {
    @Autowired
    private NotebookRepository myNotebookRepository;
    @Autowired
    private NotebookMapper myNotebookMapper;

    @Override
    public boolean isValid(final NotebookDTO khatabookDTO) {
        return myNotebookRepository.exists(Example.of(Notebook.builder().khatabookId(khatabookDTO.khatabookId()).build()));
    }

    @Override
    public NotebookDTO get(final Long noteBookId) {
        return myNotebookMapper.mapToDTO(myNotebookRepository.findById(noteBookId).orElse(null));
    }

    @Override
    public NotebookDTO create(final NotebookDTO khatabookDTO) {

        log.info("Notebook {} created.", khatabookDTO.khatabookId());
        Notebook entity = myNotebookMapper.mapToEntity(khatabookDTO, GenerationDate.creation());
        return myNotebookMapper.mapToDTO(myNotebookRepository.save(entity));
    }

    @Override
    public NotebookDTO update(final NotebookDTO khatabookDTO) {
        log.info("Notebook {} created.", khatabookDTO.khatabookId());
        myNotebookRepository.save(myNotebookMapper.mapToEntity(khatabookDTO, GenerationDate.modification()));

        log.info("Notebook {} successful created.", khatabookDTO.khatabookId());
        return getNotebookByNotebookId(khatabookDTO.noteBookId());
    }

    @Override
    public NotebookDTO delete(final Long noteBookId) {


        Notebook customer = myNotebookRepository.findById(noteBookId).orElseThrow(() -> new NotFoundException(AppEntity.KHATABOOK, noteBookId));

        myNotebookRepository.delete(customer);

        return myNotebookMapper.mapToDTO(customer);
    }

    @Override
    public List<NotebookDTO> getAll() {

        return myNotebookRepository.findAll().stream().map(myNotebookMapper::mapToDTO).toList();
    }

    @Override
    public NotebookDTO getNotebookByNotebookId(final Long noteBookId) {
        final Notebook myNotebook = myNotebookRepository.findById(noteBookId).stream().findFirst().orElse(
                null);
        return myNotebookMapper.mapToDTO(myNotebook);
    }

    @Override
    public boolean isExist(final Long noteBookId) {

        return myNotebookRepository.findById(noteBookId).isPresent();
    }

    @Override
    public List<NotebookDTO> findCustomerAllNoteBook(String khatabookId, String customerId) {
        return myNotebookMapper.mapToDTOs(myNotebookRepository.findByKhatabookIdAndCustomerId(khatabookId, customerId));
    }

    @Override
    public NotebookDTO findCustomerNoteBook(String khatabookId, String customerId, Long noteBookId) {
        return myNotebookMapper.mapToDTO(myNotebookRepository.findByKhatabookIdAndCustomerIdAndNotebookId(khatabookId, customerId, noteBookId));
    }


}
