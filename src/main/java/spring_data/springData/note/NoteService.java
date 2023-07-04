package spring_data.springData.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public void add (Note note) {
        noteRepository.save(note);
    }

    public void update(Note note) {
        if(!noteRepository.findById(note.getId()).isPresent()) {
            throw new NoSuchElementException("This note does not exist!");
        }
        noteRepository.save(note);

    }

    public void deleteById(Long id) {
        if(!noteRepository.findById(id).isPresent()) {
            throw new NoSuchElementException("This note does not exist!");
        }
        noteRepository.deleteById(id);
    }

    public synchronized Note getById(Long id) {
        return noteRepository.findById(id).orElseThrow(()->new NoSuchElementException("This note doesn't exist"));
    }

    public List<Note> listAll() {
        return noteRepository.findAll();
    }
    public synchronized List<Note> searchNote(String pattern) {
        return listAll()
                .stream()
                .filter(note ->
                        note.getContent().toLowerCase().contains(pattern.toLowerCase())
                                || note.getTitle().toLowerCase().contains(pattern.toLowerCase()))
                .toList();
    }
}
