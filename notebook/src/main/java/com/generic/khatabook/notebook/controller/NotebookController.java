package com.generic.khatabook.notebook.controller;

import com.generic.khatabook.notebook.model.NotebookDTO;
import com.generic.khatabook.notebook.model.NotebookView;
import com.generic.khatabook.notebook.model.NotebookViews;
import com.generic.khatabook.notebook.service.NotebookRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(path = "notebook-service")
public class NotebookController {
    @Autowired
    private NotebookRestService noteBookService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/notebooks")
    public List<NotebookView> noteBookList() {

        return noteBookService.getAll();
    }

    @PostMapping(value = "/{khatabookId}/{customerId}/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadDocuments(@PathVariable String khatabookId, @PathVariable String customerId, @RequestParam("file") List<MultipartFile> files) {

        try {
            NotebookView savedNotebook = noteBookService.saveAndStoreFiles(khatabookId, customerId, files);
            return ResponseEntity.ok(savedNotebook);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotebookDTO> createNotebook(@RequestBody NotebookDTO notebookDTO) {

        try {
            NotebookView savedNotebook = noteBookService.create(notebookDTO);
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{noteBookId}").buildAndExpand(savedNotebook.getNotebookId()).toUri())
                    .body(notebookDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(notebookDTO);
        }
    }

    @GetMapping("/{noteBookId}")
    public NotebookView getById(@PathVariable Long noteBookId) {
        return noteBookService.getNotebookByNotebookId(noteBookId);
    }

    @GetMapping("/{khatabookId}/{customerId}/{noteBookId}")
    public NotebookView getById(@PathVariable String khatabookId, @PathVariable String customerId, @PathVariable Long noteBookId) {
        return noteBookService.findCustomerNoteBook(khatabookId, customerId, noteBookId);
    }

    @GetMapping("/{khatabookId}/{customerId}/")
    public NotebookViews customersAllNoteBook(@PathVariable String khatabookId, @PathVariable String customerId) {

        return noteBookService.findCustomerAllNoteBook(khatabookId, customerId);
    }

    @GetMapping("/{noteBookId}/exist")
    public boolean isExist(@PathVariable Long noteBookId) {
        return noteBookService.isExist(noteBookId);
    }


    @DeleteMapping("/{noteBookId}")
    public NotebookView deleteById(@PathVariable Long noteBookId) {
        return noteBookService.delete(noteBookId);
    }


    @PutMapping("/{khatabookId}/{customerId}/{noteBookId}")
    public NotebookView updateNotebook(@PathVariable String khatabookId, @PathVariable String customerId, @PathVariable Long noteBookId, @RequestBody NotebookDTO noteBookDTO) {
        return noteBookService.update(khatabookId, customerId, noteBookId, noteBookDTO);
    }

}
