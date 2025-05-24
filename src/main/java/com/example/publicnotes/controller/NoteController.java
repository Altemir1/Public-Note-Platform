package com.example.publicnotes.controller;

import com.example.publicnotes.model.Note;
import com.example.publicnotes.service.NoteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        if (session.getAttribute("userEmail") == null) {
            return "redirect:/user/login";
        }
        String userEmail = (String) session.getAttribute("userEmail");
        List<Note> userNotes = noteService.findByEmail(userEmail);
        model.addAttribute("notes", userNotes);
        return "create";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute Note note, 
                           @RequestParam String tagString,
                           HttpSession session,
                           Model model) {
        if (session.getAttribute("userEmail") == null) {
            return "redirect:/user/login";
        }
        
        try {
            // Set the email from the session
            note.setEmail((String) session.getAttribute("userEmail"));
            
            // Convert comma-separated tags to List
            List<String> tags = Arrays.stream(tagString.split(","))
                    .map(String::trim)
                    .filter(tag -> !tag.isEmpty())
                    .collect(Collectors.toList());
            note.setTags(tags);
            
            noteService.createNote(note);
            return "redirect:/notes/create?success";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create note: " + e.getMessage());
            String userEmail = (String) session.getAttribute("userEmail");
            List<Note> userNotes = noteService.findByEmail(userEmail);
            model.addAttribute("notes", userNotes);
            return "create";
        }
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "search";
    }

    @PostMapping("/search")
    public String searchNotes(@RequestParam String tag, Model model) {
        List<Note> notes = noteService.findByTag(tag);
        model.addAttribute("notes", notes);
        model.addAttribute("searchedTag", tag);
        return "search";
    }

    @GetMapping("/user")
    public String showUserForm() {
        return "user";
    }

    @PostMapping("/user")
    public String getUserNotes(@RequestParam String email, Model model) {
        List<Note> notes = noteService.findByEmail(email);
        model.addAttribute("notes", notes);
        model.addAttribute("searchedEmail", email);
        return "user";
    }

    @GetMapping("/view/{id}")
    @ResponseBody
    public Note viewNote(@PathVariable String id) {
        return noteService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Map<String, String> deleteNote(@PathVariable String id, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        Note note = noteService.findById(id);
        
        Map<String, String> response = new HashMap<>();
        
        if (note == null) {
            response.put("status", "error");
            response.put("message", "Note not found");
            return response;
        }
        
        if (!note.getEmail().equals(userEmail)) {
            response.put("status", "error");
            response.put("message", "You can only delete your own notes");
            return response;
        }
        
        noteService.deleteNote(id);
        response.put("status", "success");
        response.put("message", "Note deleted successfully");
        return response;
    }
} 