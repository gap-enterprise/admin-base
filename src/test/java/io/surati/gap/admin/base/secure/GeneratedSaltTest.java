package io.surati.gap.admin.base.secure;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

public class GeneratedSaltTest {

	@Test
	public void generateValue() {
		MatcherAssert.assertThat(
			new GeneratedSalt().value().length(),
			new IsEqual<>(30)
		);
	}
}
