package tests;

import domain.Student;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentFileRepository;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import static org.junit.Assert.*;

public class TestAddStudent {
    private Service service;
    private StudentValidator studentValidator;
    private StudentFileRepository studentFileRepository;

    @Before
    public void setUp() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

    }

    @Test
    public void testAddStudent_ValidInput() {
        Student student = new Student("1", "John", 123, "john@example.com");

        Student result = service.addStudent(student);

        assertNotNull(result);
        assertEquals(student, result);
    }

    @Test(expected = ValidationException.class)
    public void testAddStudent_InvalidInput() {
        // Prepare invalid student
        Student invalidStudent = new Student("", "", -1, "");

        service.addStudent(invalidStudent);
    }

}
