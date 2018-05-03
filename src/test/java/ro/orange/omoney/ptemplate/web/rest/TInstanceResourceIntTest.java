package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PaymentTemplateApp;

import ro.orange.omoney.ptemplate.domain.TInstance;
import ro.orange.omoney.ptemplate.repository.TInstanceRepository;
import ro.orange.omoney.ptemplate.service.TInstanceService;
import ro.orange.omoney.ptemplate.service.dto.TInstanceDTO;
import ro.orange.omoney.ptemplate.service.mapper.TInstanceMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ro.orange.omoney.ptemplate.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TInstanceResource REST controller.
 *
 * @see TInstanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentTemplateApp.class)
public class TInstanceResourceIntTest {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TInstanceRepository tInstanceRepository;

    @Autowired
    private TInstanceMapper tInstanceMapper;

    @Autowired
    private TInstanceService tInstanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTInstanceMockMvc;

    private TInstance tInstance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TInstanceResource tInstanceResource = new TInstanceResource(tInstanceService);
        this.restTInstanceMockMvc = MockMvcBuilders.standaloneSetup(tInstanceResource)
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
    public static TInstance createEntity(EntityManager em) {
        TInstance tInstance = new TInstance()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE);
        return tInstance;
    }

    @Before
    public void initTest() {
        tInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createTInstance() throws Exception {
        int databaseSizeBeforeCreate = tInstanceRepository.findAll().size();

        // Create the TInstance
        TInstanceDTO tInstanceDTO = tInstanceMapper.toDto(tInstance);
        restTInstanceMockMvc.perform(post("/api/t-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tInstanceDTO)))
            .andExpect(status().isCreated());

        // Validate the TInstance in the database
        List<TInstance> tInstanceList = tInstanceRepository.findAll();
        assertThat(tInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        TInstance testTInstance = tInstanceList.get(tInstanceList.size() - 1);
        assertThat(testTInstance.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTInstance.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createTInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tInstanceRepository.findAll().size();

        // Create the TInstance with an existing ID
        tInstance.setId(1L);
        TInstanceDTO tInstanceDTO = tInstanceMapper.toDto(tInstance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTInstanceMockMvc.perform(post("/api/t-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tInstanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TInstance in the database
        List<TInstance> tInstanceList = tInstanceRepository.findAll();
        assertThat(tInstanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTInstances() throws Exception {
        // Initialize the database
        tInstanceRepository.saveAndFlush(tInstance);

        // Get all the tInstanceList
        restTInstanceMockMvc.perform(get("/api/t-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getTInstance() throws Exception {
        // Initialize the database
        tInstanceRepository.saveAndFlush(tInstance);

        // Get the tInstance
        restTInstanceMockMvc.perform(get("/api/t-instances/{id}", tInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tInstance.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTInstance() throws Exception {
        // Get the tInstance
        restTInstanceMockMvc.perform(get("/api/t-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTInstance() throws Exception {
        // Initialize the database
        tInstanceRepository.saveAndFlush(tInstance);
        int databaseSizeBeforeUpdate = tInstanceRepository.findAll().size();

        // Update the tInstance
        TInstance updatedTInstance = tInstanceRepository.findOne(tInstance.getId());
        // Disconnect from session so that the updates on updatedTInstance are not directly saved in db
        em.detach(updatedTInstance);
        updatedTInstance
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);
        TInstanceDTO tInstanceDTO = tInstanceMapper.toDto(updatedTInstance);

        restTInstanceMockMvc.perform(put("/api/t-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tInstanceDTO)))
            .andExpect(status().isOk());

        // Validate the TInstance in the database
        List<TInstance> tInstanceList = tInstanceRepository.findAll();
        assertThat(tInstanceList).hasSize(databaseSizeBeforeUpdate);
        TInstance testTInstance = tInstanceList.get(tInstanceList.size() - 1);
        assertThat(testTInstance.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTInstance.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTInstance() throws Exception {
        int databaseSizeBeforeUpdate = tInstanceRepository.findAll().size();

        // Create the TInstance
        TInstanceDTO tInstanceDTO = tInstanceMapper.toDto(tInstance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTInstanceMockMvc.perform(put("/api/t-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tInstanceDTO)))
            .andExpect(status().isCreated());

        // Validate the TInstance in the database
        List<TInstance> tInstanceList = tInstanceRepository.findAll();
        assertThat(tInstanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTInstance() throws Exception {
        // Initialize the database
        tInstanceRepository.saveAndFlush(tInstance);
        int databaseSizeBeforeDelete = tInstanceRepository.findAll().size();

        // Get the tInstance
        restTInstanceMockMvc.perform(delete("/api/t-instances/{id}", tInstance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TInstance> tInstanceList = tInstanceRepository.findAll();
        assertThat(tInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TInstance.class);
        TInstance tInstance1 = new TInstance();
        tInstance1.setId(1L);
        TInstance tInstance2 = new TInstance();
        tInstance2.setId(tInstance1.getId());
        assertThat(tInstance1).isEqualTo(tInstance2);
        tInstance2.setId(2L);
        assertThat(tInstance1).isNotEqualTo(tInstance2);
        tInstance1.setId(null);
        assertThat(tInstance1).isNotEqualTo(tInstance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TInstanceDTO.class);
        TInstanceDTO tInstanceDTO1 = new TInstanceDTO();
        tInstanceDTO1.setId(1L);
        TInstanceDTO tInstanceDTO2 = new TInstanceDTO();
        assertThat(tInstanceDTO1).isNotEqualTo(tInstanceDTO2);
        tInstanceDTO2.setId(tInstanceDTO1.getId());
        assertThat(tInstanceDTO1).isEqualTo(tInstanceDTO2);
        tInstanceDTO2.setId(2L);
        assertThat(tInstanceDTO1).isNotEqualTo(tInstanceDTO2);
        tInstanceDTO1.setId(null);
        assertThat(tInstanceDTO1).isNotEqualTo(tInstanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tInstanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tInstanceMapper.fromId(null)).isNull();
    }
}
