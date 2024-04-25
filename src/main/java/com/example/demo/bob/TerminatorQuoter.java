package com.example.demo.bob;

import com.example.demo.bob.post.PostProxy;
import com.example.demo.profiler.Profiling;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Profiling
@Component
public class TerminatorQuoter implements Quoter {

    @InjectRandomInt(min = 1, max = 8)
    private int times;
    private String quote = "I'll be back!";

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @PostConstruct // CommonAnnotationBeanPostProcessor from Spring handle this annotation
    //  work after postProcessBeforeInitialization() method and before postProcessAfterInitialization()
    public void init() {
        System.out.println("Phase 2");
        System.out.println(times);
    }

    public TerminatorQuoter() {
        System.out.println("Phase 1");
        System.out.println(times);
        // All that Spring configure not accessible here. Example var times always 0 in this place.
    }

    @Override
    // 3 phase construct
    @PostProxy // lets all methods run automatic when whole context ready and configure
    public void sayQuote() {
        System.out.println("Phase 3");
        for (int i = 0; i < times; i++) {
            System.out.println("message = " + quote);
        }
    }
}
