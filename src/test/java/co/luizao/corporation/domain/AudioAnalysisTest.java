package co.luizao.corporation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.luizao.corporation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AudioAnalysisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AudioAnalysis.class);
        AudioAnalysis audioAnalysis1 = new AudioAnalysis();
        audioAnalysis1.setId(1L);
        AudioAnalysis audioAnalysis2 = new AudioAnalysis();
        audioAnalysis2.setId(audioAnalysis1.getId());
        assertThat(audioAnalysis1).isEqualTo(audioAnalysis2);
        audioAnalysis2.setId(2L);
        assertThat(audioAnalysis1).isNotEqualTo(audioAnalysis2);
        audioAnalysis1.setId(null);
        assertThat(audioAnalysis1).isNotEqualTo(audioAnalysis2);
    }
}
