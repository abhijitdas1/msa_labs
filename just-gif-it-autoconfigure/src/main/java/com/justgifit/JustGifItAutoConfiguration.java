package com.justgifit;

import java.io.File;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.justgifit.services.ConverterService;
import com.justgifit.services.GifEncoderService;
import com.justgifit.services.VideoDecoderService;
import com.madgag.gif.fmsware.AnimatedGifEncoder;

@Configuration
@ConditionalOnClass({FFmpegFrameGrabber.class, AnimatedGifEncoder.class})
@ConditionalOnProperty(prefix = "spring.aop", name = "auto", havingValue = "true", matchIfMissing = true)
public class JustGifItAutoConfiguration {
	
    @Value("${multipart.location}/gif/")
    private String gifLocation;
    
    @Bean
    @ConditionalOnProperty(prefix="com.justgifit", name="create-result-dir")
    public Boolean createResultDir() {
        File gifFolder = new File(gifLocation);
        if (!gifFolder.exists()) {
            gifFolder.mkdir();
        }
        return true;
    }
    
	@Bean
	@ConditionalOnMissingBean(VideoDecoderService.class)
	public VideoDecoderService videoDecoderService() {
		return new VideoDecoderService();
	}

	@Bean
	@ConditionalOnMissingBean(GifEncoderService.class)
	public GifEncoderService gifEncoderService() {
		return new GifEncoderService();
	}
	
	@Bean
	@ConditionalOnMissingBean(ConverterService.class)
	public ConverterService converterService() {
		return new ConverterService();
	}	
}
