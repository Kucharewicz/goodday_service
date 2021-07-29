package pl.project.goodday;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.project.goodday.controllers.MainController;
import pl.project.goodday.repositories.UserRepository;
import pl.project.goodday.services.MyUserDetailsService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(MainController.class)
public class SmokeTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @MockBean
    private MyUserDetailsService myUserDetailService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void shouldPassWhenAnyUserGoingToCommonEndpoint() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/");
        MvcResult result = mockMvc.perform(request).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("<h1>Welcome</h1>");
    }

    @Test
    public void shouldPassWhenSomeoneWithCorrectCredentialsComesToThisEndpoint() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/user").with(user("iwona").password("qwerty").roles("USER"));
        MvcResult result = mockMvc.perform(request).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("<h1>Welcome User</h1>");
    }

    @Test
    public void shouldPassWhenSomeoneWithAdminCredentialsComesToThisEndpoint() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/admin").with(user("admin").password("admin").roles("ADMIN"));
        MvcResult result = mockMvc.perform(request).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("<h1>Welcome Admin</h1>");
    }



}
