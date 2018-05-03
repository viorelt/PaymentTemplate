package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PaymentTemplateApp;

import ro.orange.omoney.ptemplate.domain.PostCommand;
import ro.orange.omoney.ptemplate.repository.PostCommandRepository;
import ro.orange.omoney.ptemplate.service.PostCommandService;
import ro.orange.omoney.ptemplate.service.dto.PostCommandDTO;
import ro.orange.omoney.ptemplate.service.mapper.PostCommandMapper;
import ro.orange.omoney.ptemplate.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static ro.orange.omoney.ptemplate.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PostCommandResource REST controller.
 *
 * @see PostCommandResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentTemplateApp.class)
public class PostCommandResourceIntTest {

    private static final String DEFAULT_LABEL_KEY = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ENDPOINT = "AAAAAAAAAA";
    private static final String UPDATED_ENDPOINT = "BBBBBBBBBB";

    @Autowired
    private PostCommandRepository postCommandRepository;

    @Autowired
    private PostCommandMapper postCommandMapper;

    @Autowired
    private PostCommandService postCommandService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPostCommandMockMvc;

    private PostCommand postCommand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PostCommandResource postCommandResource = new PostCommandResource(postCommandService);
        this.restPostCommandMockMvc = MockMvcBuilders.standaloneSetup(postCommandResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostCommand createEntity(EntityManager em) {
        PostCommand postCommand = new PostCommand()
            .labelKey(DEFAULT_LABEL_KEY)
            .endpoint(DEFAULT_ENDPOINT);
        return postCommand;
    }

    @Before
    public void initTest() {
        postCommand = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostCommand() throws Exception {
        int databaseSizeBeforeCreate = postCommandRepository.findAll().size();

        // Create the PostCommand
        PostCommandDTO postCommandDTO = postCommandMapper.toDto(postCommand);
        restPostCommandMockMvc.perform(post("/api/post-commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postCommandDTO)))
            .andExpect(status().isCreated());

        // Validate the PostCommand in the database
        List<PostCommand> postCommandList = postCommandRepository.findAll();
        assertThat(postCommandList).hasSize(databaseSizeBeforeCreate + 1);
        PostCommand testPostCommand = postCommandList.get(postCommandList.size() - 1);
        assertThat(testPostCommand.getLabelKey()).isEqualTo(DEFAULT_LABEL_KEY);
        assertThat(testPostCommand.getEndpoint()).isEqualTo(DEFAULT_ENDPOINT);
    }

    @Test
    @Transactional
    public void createPostCommandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postCommandRepository.findAll().size();

        // Create the PostCommand with an existing ID
        postCommand.setId(1L);
        PostCommandDTO postCommandDTO = postCommandMapper.toDto(postCommand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostCommandMockMvc.perform(post("/api/post-commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postCommandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostCommand in the database
        List<PostCommand> postCommandList = postCommandRepository.findAll();
        assertThat(postCommandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPostCommands() throws Exception {
        // Initialize the database
        postCommandRepository.saveAndFlush(postCommand);

        // Get all the postCommandList
        restPostCommandMockMvc.perform(get("/api/post-commands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postCommand.getId().intValue())))
            .andExpect(jsonPath("$.[*].labelKey").value(hasItem(DEFAULT_LABEL_KEY.toString())))
            .andExpect(jsonPath("$.[*].endpoint").value(hasItem(DEFAULT_ENDPOINT.toString())));
    }

    @Test
    @Transactional
    public void getPostCommand() throws Exception {
        // Initialize the database
        postCommandRepository.saveAndFlush(postCommand);

        // Get the postCommand
        restPostCommandMockMvc.perform(get("/api/post-commands/{id}", postCommand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(postCommand.getId().intValue()))
            .andExpect(jsonPath("$.labelKey").value(DEFAULT_LABEL_KEY.toString()))
            .andExpect(jsonPath("$.endpoint").value(DEFAULT_ENDPOINT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPostCommand() throws Exception {
        // Get the postCommand
        restPostCommandMockMvc.perform(get("/api/post-commands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostCommand() throws Exception {
        // Initialize the database
        postCommandRepository.saveAndFlush(postCommand);
        int databaseSizeBeforeUpdate = postCommandRepository.findAll().size();

        // Update the postCommand
        PostCommand updatedPostCommand = postCommandRepository.findOne(postCommand.getId());
        // Disconnect from session so that the updates on updatedPostCommand are not directly saved in db
        em.detach(updatedPostCommand);
        updatedPostCommand
            .labelKey(UPDATED_LABEL_KEY)
            .endpoint(UPDATED_ENDPOINT);
        PostCommandDTO postCommandDTO = postCommandMapper.toDto(updatedPostCommand);

        restPostCommandMockMvc.perform(put("/api/post-commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postCommandDTO)))
            .andExpect(status().isOk());

        // Validate the PostCommand in the database
        List<PostCommand> postCommandList = postCommandRepository.findAll();
        assertThat(postCommandList).hasSize(databaseSizeBeforeUpdate);
        PostCommand testPostCommand = postCommandList.get(postCommandList.size() - 1);
        assertThat(testPostCommand.getLabelKey()).isEqualTo(UPDATED_LABEL_KEY);
        assertThat(testPostCommand.getEndpoint()).isEqualTo(UPDATED_ENDPOINT);
    }

    @Test
    @Transactional
    public void updateNonExistingPostCommand() throws Exception {
        int databaseSizeBeforeUpdate = postCommandRepository.findAll().size();

        // Create the PostCommand
        PostCommandDTO postCommandDTO = postCommandMapper.toDto(postCommand);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPostCommandMockMvc.perform(put("/api/post-commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postCommandDTO)))
            .andExpect(status().isCreated());

        // Validate the PostCommand in the database
        List<PostCommand> postCommandList = postCommandRepository.findAll();
        assertThat(postCommandList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePostCommand() throws Exception {
        // Initialize the database
        postCommandRepository.saveAndFlush(postCommand);
        int databaseSizeBeforeDelete = postCommandRepository.findAll().size();

        // Get the postCommand
        restPostCommandMockMvc.perform(delete("/api/post-commands/{id}", postCommand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PostCommand> postCommandList = postCommandRepository.findAll();
        assertThat(postCommandList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostCommand.class);
        PostCommand postCommand1 = new PostCommand();
        postCommand1.setId(1L);
        PostCommand postCommand2 = new PostCommand();
        postCommand2.setId(postCommand1.getId());
        assertThat(postCommand1).isEqualTo(postCommand2);
        postCommand2.setId(2L);
        assertThat(postCommand1).isNotEqualTo(postCommand2);
        postCommand1.setId(null);
        assertThat(postCommand1).isNotEqualTo(postCommand2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostCommandDTO.class);
        PostCommandDTO postCommandDTO1 = new PostCommandDTO();
        postCommandDTO1.setId(1L);
        PostCommandDTO postCommandDTO2 = new PostCommandDTO();
        assertThat(postCommandDTO1).isNotEqualTo(postCommandDTO2);
        postCommandDTO2.setId(postCommandDTO1.getId());
        assertThat(postCommandDTO1).isEqualTo(postCommandDTO2);
        postCommandDTO2.setId(2L);
        assertThat(postCommandDTO1).isNotEqualTo(postCommandDTO2);
        postCommandDTO1.setId(null);
        assertThat(postCommandDTO1).isNotEqualTo(postCommandDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(postCommandMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(postCommandMapper.fromId(null)).isNull();
    }
}
