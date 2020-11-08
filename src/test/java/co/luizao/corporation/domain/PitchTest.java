package co.luizao.corporation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.luizao.corporation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PitchTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pitch.class);
        Pitch pitch1 = new Pitch();
        pitch1.setId(1L);
        Pitch pitch2 = new Pitch();
        pitch2.setId(pitch1.getId());
        assertThat(pitch1).isEqualTo(pitch2);
        pitch2.setId(2L);
        assertThat(pitch1).isNotEqualTo(pitch2);
        pitch1.setId(null);
        assertThat(pitch1).isNotEqualTo(pitch2);
    }
}
