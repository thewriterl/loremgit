package co.luizao.corporation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.luizao.corporation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AudioFeaturesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AudioFeatures.class);
        AudioFeatures audioFeatures1 = new AudioFeatures();
        audioFeatures1.setId(1L);
        AudioFeatures audioFeatures2 = new AudioFeatures();
        audioFeatures2.setId(audioFeatures1.getId());
        assertThat(audioFeatures1).isEqualTo(audioFeatures2);
        audioFeatures2.setId(2L);
        assertThat(audioFeatures1).isNotEqualTo(audioFeatures2);
        audioFeatures1.setId(null);
        assertThat(audioFeatures1).isNotEqualTo(audioFeatures2);
    }
}
