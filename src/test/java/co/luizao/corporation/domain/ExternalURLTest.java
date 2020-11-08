package co.luizao.corporation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.luizao.corporation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ExternalURLTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExternalURL.class);
        ExternalURL externalURL1 = new ExternalURL();
        externalURL1.setId(1L);
        ExternalURL externalURL2 = new ExternalURL();
        externalURL2.setId(externalURL1.getId());
        assertThat(externalURL1).isEqualTo(externalURL2);
        externalURL2.setId(2L);
        assertThat(externalURL1).isNotEqualTo(externalURL2);
        externalURL1.setId(null);
        assertThat(externalURL1).isNotEqualTo(externalURL2);
    }
}
