package com.generic.khatabook.notebook.service.impl;

import com.generic.khatabook.notebook.entity.FileDetail;
import com.generic.khatabook.notebook.entity.Notebook;
import com.generic.khatabook.notebook.exceptions.AppEntity;
import com.generic.khatabook.notebook.exceptions.NotFoundException;
import com.generic.khatabook.notebook.model.*;
import com.generic.khatabook.notebook.repository.NotebookRepository;
import com.generic.khatabook.notebook.service.NotebookRestService;
import com.generic.khatabook.notebook.service.StorageService;
import com.generic.khatabook.notebook.service.mapper.NotebookMapper;
import com.generic.khatabook.notebook.service.mapper.NotebookViewMapper;
import com.generic.khatabook.notebook.service.proxy.CustomerProxyService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotebookRestServiceImpl implements NotebookRestService {

    @Autowired
    private NotebookRepository myNotebookRepository;
    @Autowired
    private NotebookViewMapper myNotebookViewMapper;
    @Autowired
    private NotebookMapper myNotebookMapper;

    @Autowired
    private CustomerProxyService myCustomerProxyService;

    @Autowired
    private StorageService storageService;


    @Override
    public NotebookView create(final NotebookDTO dto) {
        final var customer = myCustomerProxyService.getCustomerByCustomerId(dto.khatabookId(), dto.customerId());

        if (Objects.isNull(customer)) {
            throw new NotFoundException(AppEntity.CUSTOMER, dto.customerId());
        }
        log.info("Notebook {} created.", dto.noteBookId());
        return myNotebookViewMapper.mapToDTO(myNotebookRepository.save(myNotebookMapper.mapToEntity(dto)));
    }

    @Override
    public NotebookView update(String khatabookId, String customerId, Long noteBookId, NotebookDTO dto) {
        Notebook customer = myNotebookRepository.findById(noteBookId).orElseThrow(() -> new NotFoundException(AppEntity.NOTEBOOK, noteBookId));

        if (!customer.getDescription().equals(dto.description())) {
            customer.setDescription(dto.description());
            customer.setUpdatedOn(LocalDateTime.now(Clock.systemDefaultZone()));
        }

        return myNotebookViewMapper.mapToDTO(myNotebookRepository.save(customer));
    }


    @Override
    @Transactional
    public NotebookView saveAndStoreFiles(String khatabookId, String customerId, List<MultipartFile> files) {
        List<FileDetail> fileDetails = new ArrayList<>();
        LocalDateTime createdOn = LocalDateTime.now(Clock.systemDefaultZone());

        Notebook notebook = Notebook.builder()
                .customerId(customerId)
                .khatabookId(khatabookId)
                .createdOn(createdOn)
                .status("Y")
                .files(fileDetails)
                .build();

        for (MultipartFile file : files) {
            Path destinationFilePath = storageService.store(khatabookId, customerId, file);
            fileDetails.add(FileDetail.builder()
                    .filePath(destinationFilePath.getParent().toString())
                    .fileName(destinationFilePath.getFileName().toString())
                    .createdOn(createdOn)
                    .notebook(notebook)
                    .build());
        }
        String fileNames = fileDetails.stream().map(FileDetail::getFileName).collect(Collectors.joining(","));
//        final NotebookDTO dto = new NotebookDTO(null, customerId, khatabookId, fileNames, createdOn, "Y");
        notebook.setFiles(fileDetails);
        notebook.setDescription(fileNames + " Uploaded");
        return myNotebookViewMapper.mapToDTO(myNotebookRepository.save(notebook));
    }

    @Override
    public NotebookView findCustomerNoteBook(String khatabookId, String customerId, Long noteBookId) {
        return myNotebookViewMapper.mapToDTO(myNotebookRepository.findByKhatabookIdAndCustomerIdAndNotebookId(khatabookId, customerId, noteBookId));
    }

    @Override
    public NotebookViews findCustomerAllNoteBook(String khatabookId, String customerId) {


        final var customer = myCustomerProxyService.getCustomerByCustomerId(khatabookId, customerId);

        if (Objects.isNull(customer)) {
            throw new NotFoundException(AppEntity.CUSTOMER, customerId);
        }
        List<NotebookItemView> itemViews = myNotebookRepository.findByKhatabookIdAndCustomerId(khatabookId, customerId).stream().map(this::covertNotebookItem).toList();

        return new NotebookViews(new CustomerView(customer.getCustomer().customerId(), customer.getKhatabookId(), customer.getFullName()), new NotebookItemsView(itemViews));
    }

    private NotebookItemView covertNotebookItem(Notebook notebook) {
        List<String> listOfFilePath = notebook.getFiles().stream().map(x -> x.getFilePath()+ File.separator + x.getFileName()).toList();
        return NotebookItemView.builder()
                .bookId(notebook.getBookId())
                .notebookId(notebook.getNotebookId())
                .createdOn(notebook.getCreatedOn())
                .updatedOn(notebook.getUpdatedOn())
                .deletedOn(notebook.getDeletedOn())
                .status(notebook.getStatus())
                .filePaths(listOfFilePath)
                .description(notebook.getDescription())
                .build();
    }


    @Override
    public NotebookView get(final Long noteBookId) {
        return myNotebookViewMapper.mapToDTO(myNotebookRepository.findById(noteBookId).orElse(null));
    }


    @Override
    public NotebookView delete(final Long noteBookId) {


        Notebook customer = myNotebookRepository.findById(noteBookId).orElseThrow(() -> new NotFoundException(AppEntity.NOTEBOOK, noteBookId));

        myNotebookRepository.delete(customer);

        return myNotebookViewMapper.mapToDTO(customer);
    }

    @Override
    public List<NotebookView> getAll() {

        return myNotebookRepository.findAll().stream().map(myNotebookViewMapper::mapToDTO).toList();
    }

    @Override
    public NotebookView getNotebookByNotebookId(final Long noteBookId) {
        final Notebook myNotebook = myNotebookRepository.findById(noteBookId).stream().findFirst().orElse(
                null);
        return myNotebookViewMapper.mapToDTO(myNotebook);
    }

    @Override
    public boolean isExist(final Long noteBookId) {

        return myNotebookRepository.findById(noteBookId).isPresent();
    }

    @Override
    public boolean isValid(NotebookDTO customer) {
        return false;
    }


}
