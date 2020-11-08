package co.luizao.corporation.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, co.luizao.corporation.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, co.luizao.corporation.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, co.luizao.corporation.domain.User.class.getName());
            createCache(cm, co.luizao.corporation.domain.Authority.class.getName());
            createCache(cm, co.luizao.corporation.domain.User.class.getName() + ".authorities");
            createCache(cm, co.luizao.corporation.domain.Image.class.getName());
            createCache(cm, co.luizao.corporation.domain.Genre.class.getName());
            createCache(cm, co.luizao.corporation.domain.ExternalURL.class.getName());
            createCache(cm, co.luizao.corporation.domain.Artist.class.getName());
            createCache(cm, co.luizao.corporation.domain.Artist.class.getName() + ".genres");
            createCache(cm, co.luizao.corporation.domain.Album.class.getName());
            createCache(cm, co.luizao.corporation.domain.Album.class.getName() + ".genres");
            createCache(cm, co.luizao.corporation.domain.Album.class.getName() + ".artists");
            createCache(cm, co.luizao.corporation.domain.Track.class.getName());
            createCache(cm, co.luizao.corporation.domain.Track.class.getName() + ".artists");
            createCache(cm, co.luizao.corporation.domain.TimeInterval.class.getName());
            createCache(cm, co.luizao.corporation.domain.AudioAnalysis.class.getName());
            createCache(cm, co.luizao.corporation.domain.AudioAnalysis.class.getName() + ".sections");
            createCache(cm, co.luizao.corporation.domain.AudioAnalysis.class.getName() + ".bars");
            createCache(cm, co.luizao.corporation.domain.AudioAnalysis.class.getName() + ".beats");
            createCache(cm, co.luizao.corporation.domain.AudioAnalysis.class.getName() + ".segments");
            createCache(cm, co.luizao.corporation.domain.AudioAnalysis.class.getName() + ".tatums");
            createCache(cm, co.luizao.corporation.domain.Section.class.getName());
            createCache(cm, co.luizao.corporation.domain.Segment.class.getName());
            createCache(cm, co.luizao.corporation.domain.Segment.class.getName() + ".pitches");
            createCache(cm, co.luizao.corporation.domain.Segment.class.getName() + ".timbres");
            createCache(cm, co.luizao.corporation.domain.Pitch.class.getName());
            createCache(cm, co.luizao.corporation.domain.Pitch.class.getName() + ".segments");
            createCache(cm, co.luizao.corporation.domain.Timbre.class.getName());
            createCache(cm, co.luizao.corporation.domain.Timbre.class.getName() + ".segments");
            createCache(cm, co.luizao.corporation.domain.AudioFeatures.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
