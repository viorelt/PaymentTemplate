package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PaymentTemplateApp;

import ro.orange.omoney.ptemplate.domain.TVersion;
import ro.orange.omoney.ptemplate.repository.TVersionRepository;
import ro.orange.omoney.ptemplate.service.TVersionService;
import ro.orange.omoney.ptemplate.service.dto.TVersionDTO;
import ro.orange.omoney.ptemplate.service.mapper.TVersionMapper;
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
 * Test class for the TVersionResource REST controller.
 *
 * @see TVersionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentTemplateApp.class)
public class TVersionResourceIntTest {

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TVersionRepository tVersionRepository;

    @Autowired
    private TVersionMapper tVersionMapper;

    @Autowired
    private TVersionService tVersionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTVersionMockMvc;

    private TVersion tVersion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TVersionResource tVersionResource = new TVersionResource(tVersionService);
        this.restTVersionMockMvc = MockMvcBuilders.standaloneSetup(tVersionResource)
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
    public static TVersion createEntity(EntityManager em) {
        TVersion tVersion = new TVersion()
            .version(DEFAULT_VERSION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE);
        return tVersion;
    }

    @Before
    public void initTest() {
        tVersion = createEntity(em);
    }

    @Test
    @Transactional
    public void createTVersion() throws Exception {
        int databaseSizeBeforeCreate = tVersionRepository.findAll().size();

        // Create the TVersion
        TVersionDTO tVersionDTO = tVersionMapper.toDto(tVersion);
        restTVersionMockMvc.perform(post("/api/t-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tVersionDTO)))
            .andExpect(status().isCreated());

        // Validate the TVersion in the database
        List<TVersion> tVersionList = tVersionRepository.findAll();
        assertThat(tVersionList).hasSize(databaseSizeBeforeCreate + 1);
        TVersion testTVersion = tVersionList.get(tVersionList.size() - 1);
        assertThat(testTVersion.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testTVersion.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTVersion.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createTVersionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tVersionRepository.findAll().size();

        // Create the TVersion with an existing ID
        tVersion.setId(1L);
        TVersionDTO tVersionDTO = tVersionMapper.toDto(tVersion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTVersionMockMvc.perform(post("/api/t-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tVersionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TVersion in the database
        List<TVersion> tVersionList = tVersionRepository.findAll();
        assertThat(tVersionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTVersions() throws Exception {
        // Initialize the database
        tVersionRepository.saveAndFlush(tVersion);

        // Get all the tVersionList
        restTVersionMockMvc.perform(get("/api/t-versions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tVersion.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getTVersion() throws Exception {
        // Initialize the database
        tVersionRepository.saveAndFlush(tVersion);

        // Get the tVersion
        restTVersionMockMvc.perform(get("/api/t-versions/{id}", tVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tVersion.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTVersion() throws Exception {
        // Get the tVersion
        restTVersionMockMvc.perform(get("/api/t-versions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTVersion() throws Exception {
        // Initialize the database
        tVersionRepository.saveAndFlush(tVersion);
        int databaseSizeBeforeUpdate = tVersionRepository.findAll().size();

        // Update the tVersion
        TVersion updatedTVersion = tVersionRepository.findOne(tVersion.getId());
        // Disconnect from session so that the updates on updatedTVersion are not directly saved in db
        em.detach(updatedTVersion);
        updatedTVersion
            .version(UPDATED_VERSION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);
        TVersionDTO tVersionDTO = tVersionMapper.toDto(updatedTVersion);

        restTVersionMockMvc.perform(put("/api/t-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tVersionDTO)))
            .andExpect(status().isOk());

        // Validate the TVersion in the database
        List<TVersion> tVersionList = tVersionRepository.findAll();
        assertThat(tVersionList).hasSize(databaseSizeBeforeUpdate);
        TVersion testTVersion = tVersionList.get(tVersionList.size() - 1);
        assertThat(testTVersion.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testTVersion.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTVersion.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTVersion() throws Exception {
        int databaseSizeBeforeUpdate = tVersionRepository.findAll().size();

        // Create the TVersion
        TVersionDTO tVersionDTO = tVersionMapper.toDto(tVersion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTVersionMockMvc.perform(put("/api/t-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tVersionDTO)))
            .andExpect(status().isCreated());

        // Validate the TVersion in the database
        List<TVersion> tVersionList = tVersionRepository.findAll();
        assertThat(tVersionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTVersion() throws Exception {
        // Initialize the database
        tVersionRepository.saveAndFlush(tVersion);
        int databaseSizeBeforeDelete = tVersionRepository.findAll().size();

        // Get the tVersion
        restTVersionMockMvc.perform(delete("/api/t-versions/{id}", tVersion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TVersion> tVersionList = tVersionRepository.findAll();
        assertThat(tVersionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TVersion.class);
        TVersion tVersion1 = new TVersion();
        tVersion1.setId(1L);
        TVersion tVersion2 = new TVersion();
        tVersion2.setId(tVersion1.getId());
        assertThat(tVersion1).isEqualTo(tVersion2);
        tVersion2.setId(2L);
        assertThat(tVersion1).isNotEqualTo(tVersion2);
        tVersion1.setId(null);
        assertThat(tVersion1).isNotEqualTo(tVersion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TVersionDTO.class);
        TVersionDTO tVersionDTO1 = new TVersionDTO();
        tVersionDTO1.setId(1L);
        TVersionDTO tVersionDTO2 = new TVersionDTO();
        assertThat(tVersionDTO1).isNotEqualTo(tVersionDTO2);
        tVersionDTO2.setId(tVersionDTO1.getId());
        assertThat(tVersionDTO1).isEqualTo(tVersionDTO2);
        tVersionDTO2.setId(2L);
        assertThat(tVersionDTO1).isNotEqualTo(tVersionDTO2);
        tVersionDTO1.setId(null);
        assertThat(tVersionDTO1).isNotEqualTo(tVersionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tVersionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tVersionMapper.fromId(null)).isNull();
    }
}
