package co.luizao.corporation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.luizao.corporation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class TimeIntervalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeInterval.class);
        TimeInterval timeInterval1 = new TimeInterval();
        timeInterval1.setId(1L);
        TimeInterval timeInterval2 = new TimeInterval();
        timeInterval2.setId(timeInterval1.getId());
        assertThat(timeInterval1).isEqualTo(timeInterval2);
        timeInterval2.setId(2L);
        assertThat(timeInterval1).isNotEqualTo(timeInterval2);
        timeInterval1.setId(null);
        assertThat(timeInterval1).isNotEqualTo(timeInterval2);
    }
}
