package com.singleton.playlistmaker;

import com.singleton.playlistmaker.service.Parser;
import com.singleton.playlistmaker.service.ServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@AllArgsConstructor
public class PlaylistMakerApplication implements CommandLineRunner {

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        new SpringApplicationBuilder(PlaylistMakerApplication.class).web(WebApplicationType.NONE).build().run(args);
    }

    @Override
    public void run(String... args) {
        String data = applicationContext.getBean("coreRadioClient", ServiceClient.class).getData();
        System.out.println(applicationContext.getBean("coreRadioParser", Parser.class).parse(data));
    }
}
