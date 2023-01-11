package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for find faculty with id = {}", id);
        try {
            Faculty faculty = facultyRepository.findById(id).get();
            return faculty;
        } catch (NoSuchElementException e) {
            logger.error("There is not faculty with id = {}", id);
            throw new NotFoundException(null);
        }
//        return facultyRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException(null));
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty with id = {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findFacultiesByColor(String color) {
        logger.info("Was invoked method for find faculties by color {}", color);
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(
            String name, String color) {
        logger.info("Was invoked method for find faculties " +
                "by name {} (ignore case) or color {} (ignore case)",
                name, color);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Student> getFacultyStudents(long id) {
        logger.info("Was invoked method for find students of the faculty " +
                "with id = {}", id);
        try {
            Collection<Student> students = facultyRepository.findById(id)
                    .map(Faculty::getStudents).get();
            return students;
        } catch (NoSuchElementException e) {
            logger.error("There is not faculty with id = {}", id);
            throw new NotFoundException(null);
        }
//        return facultyRepository.findById(id)
//                .map(Faculty::getStudents)
//                .orElseThrow(() -> new NotFoundException(null));
    }

    public String getLongestFacultyName() {
        return facultyRepository.findAll()
                .parallelStream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .get();
    }

    public Integer getInteger() {
        return Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
    }
}
