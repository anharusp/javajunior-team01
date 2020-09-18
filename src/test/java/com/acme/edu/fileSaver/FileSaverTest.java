package com.acme.edu.fileSaver;

import com.acme.edu.message.ChatMessage;
import com.acme.edu.server.saver.FileSaver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class FileSaverTest {
    File testFile;
    FileSaver fileSaver;

    @Before
    public void setUp() throws FileNotFoundException {
        testFile = new File("testfile.log");
        fileSaver = new FileSaver(testFile);
    }
    @After
    public void Flush() throws IOException {
        this.fileSaver.close();
        this.testFile.delete();
    }


    @Test
    public void shouldCreateFileForFileSaverWhenConstructorCalls() {
        assertThat(testFile.exists());
    }

    @Test
    public void shouldSaveMessageWhenSaveMethodCalls() throws IOException {
        ChatMessage dummy = new ChatMessage("dummy", "dummy", System.currentTimeMillis());
        File testFile = new File("testSaver.log");
        FileSaver fileSaver = new FileSaver(testFile);
        fileSaver.save(dummy);
        assertThat(testFile.length() > 4);
        fileSaver.close();
        testFile.delete();
    }

    @Test(expected = IOException.class)
    public void shouldThrowExceptionWhenNoFileExists() throws IOException {
        new FileSaver("");
    }
}
