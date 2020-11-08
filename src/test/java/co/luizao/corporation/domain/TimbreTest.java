package co.luizao.corporation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.luizao.corporation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class TimbreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Timbre.class);
        Timbre timbre1 = new Timbre();
        timbre1.setId(1L);
        Timbre timbre2 = new Timbre();
        timbre2.setId(timbre1.getId());
        assertThat(timbre1).isEqualTo(timbre2);
        timbre2.setId(2L);
        assertThat(timbre1).isNotEqualTo(timbre2);
        timbre1.setId(null);
        assertThat(timbre1).isNotEqualTo(timbre2);
    }
}
