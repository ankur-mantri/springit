package com.vega.demo.bootstrap;

import com.vega.demo.domain.Comment;
import com.vega.demo.domain.Link;
import com.vega.demo.domain.Role;
import com.vega.demo.domain.UserSpringIt;
import com.vega.demo.repository.CommentRepository;
import com.vega.demo.repository.LinkRepository;
import com.vega.demo.repository.RoleRepository;
import com.vega.demo.repository.UserRepository;
import com.vega.demo.service.BeanUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Profile("dev")
@Component
public class DatabaseLoader implements CommandLineRunner {
    private LinkRepository linkRepository;
    private CommentRepository commentRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private Map<String,UserSpringIt> users = new HashMap<>();

    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository, UserRepository userRepository,
                          RoleRepository roleRepository) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception{

        addUsersAndRoles();

        List<String> randomComments = new ArrayList<>();
        randomComments.add("This is funny link. Wish I could write a joke her :) :) :)");
        randomComments.add("This is awesome. I am jumping like a JACK");
        randomComments.add("This is random. Writer is going all over.");
        randomComments.add("Do not visit this ever. Did you already clicked the link??");
        randomComments.add("This is super site. Go there if you are a Superman or Superwoman or Superwhatever");


        Map<String,String> links = new HashMap<>();
        links.put("Securing Spring Boot APIs and SPAs with OAuth 2.0","https://auth0.com/blog/securing-spring-boot-apis-and-spas-with-oauth2/?utm_source=reddit&utm_medium=sc&utm_campaign=springboot_spa_securing");
        links.put("Easy way to detect Device in Java Web Application using Spring Mobile - Source code to download from GitHub","https://www.opencodez.com/java/device-detection-using-spring-mobile.htm");
        links.put("Tutorial series about building microservices with SpringBoot (with Netflix OSS)","https://medium.com/@marcus.eisele/implementing-a-microservice-architecture-with-spring-boot-intro-cdb6ad16806c");
        links.put("Detailed steps to send encrypted email using Java / Spring Boot - Source code to download from GitHub","https://www.opencodez.com/java/send-encrypted-email-using-java.htm");
        links.put("Build a Secure Progressive Web App With Spring Boot and React","https://dzone.com/articles/build-a-secure-progressive-web-app-with-spring-boo");
        links.put("Building Your First Spring Boot Web Application - DZone Java","https://dzone.com/articles/building-your-first-spring-boot-web-application-ex");
        links.put("Building Microservices with Spring Boot Fat (Uber) Jar","https://jelastic.com/blog/building-microservices-with-spring-boot-fat-uber-jar/");
        links.put("Spring Cloud GCP 1.0 Released","https://cloud.google.com/blog/products/gcp/calling-java-developers-spring-cloud-gcp-1-0-is-now-generally-available");
        links.put("Simplest way to Upload and Download Files in Java with Spring Boot - Code to download from Github","https://www.opencodez.com/uncategorized/file-upload-and-download-in-java-spring-boot.htm");
        links.put("Add Social Login to Your Spring Boot 2.0 app","https://developer.okta.com/blog/2018/07/24/social-spring-boot");
        links.put("File download example using Spring REST Controller","https://www.jeejava.com/file-download-example-using-spring-rest-controller/");

        links.forEach((k,v) -> {
            UserSpringIt u1 = users.get("user@gmail.com");
            UserSpringIt u2 = users.get("master@gmail.com");
            Link link = new Link(k,v);
            if(k.startsWith("Build")) {
                link.setUserSpringIt(u1);
            } else {
                link.setUserSpringIt(u2);
            }

            linkRepository.save(link);

            int n = (int) (Math.random()*randomComments.size());
            for (int i = 0; i < n; i++) {
               Comment comment = new Comment(randomComments.get(i), link);
                commentRepository.save(comment);
                link.addComment(comment);
            }


        });

        long linkCount = linkRepository.count();
        System.out.println("Number of links in the database: " + linkCount );

    }

    private void addUsersAndRoles() {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        BCryptPasswordEncoder encoder = BeanUtil.getBean(BCryptPasswordEncoder.class);
        String secret = "{bcrypt}" + encoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);

        UserSpringIt userSpringIt = new UserSpringIt("user@gmail.com",secret,true,"Smith","Jones","SJoe");
        userSpringIt.addRole(userRole);
        userSpringIt.setConfirmPassword(secret);
        userRepository.save(userSpringIt);
        users.put("user@gmail.com",userSpringIt);

        UserSpringIt admin = new UserSpringIt("admin@gmail.com",secret,true,"Randhawa","Guruji","DBoss");
        admin.addRole(adminRole);
        admin.setConfirmPassword(secret);
        userRepository.save(admin);
        users.put("admin@gmail.com",admin);

        UserSpringIt master = new UserSpringIt("master@gmail.com",secret,true,"Mickey","Mouse","wife");
        master.addRoles(new HashSet<>(Arrays.asList(userRole,adminRole)));
        master.setConfirmPassword(secret);
        userRepository.save(master);
        users.put("master@gmail.com",master);
    }


}
