package br.com.bws.portalnoticias.config;

import br.com.bws.portalnoticias.application.service.AnuncioService;
import br.com.bws.portalnoticias.application.service.CloudinaryService;
import br.com.bws.portalnoticias.application.service.NoticiaService;
import br.com.bws.portalnoticias.application.service.UsuarioService;
import br.com.bws.portalnoticias.domain.entity.Usuario;
import br.com.bws.portalnoticias.domain.model.AdPosition;
import br.com.bws.portalnoticias.domain.model.Category;
import br.com.bws.portalnoticias.domain.model.Type;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDate;

@Component
public class DatabaseInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Value("${environment.portfolio}")
    private boolean portfolio;

    private final NoticiaService noticiaService;
    private final AnuncioService anuncioService;
    private final UsuarioService usuarioService;
    private final CloudinaryService cloudinaryService;
    private final BCryptPasswordEncoder passwordEncoder;

    public DatabaseInitializer(NoticiaService noticiaService, AnuncioService anuncioService, UsuarioService usuarioService, CloudinaryService cloudinaryService, BCryptPasswordEncoder passwordEncoder) {
        this.noticiaService = noticiaService;
        this.anuncioService = anuncioService;
        this.usuarioService = usuarioService;
        this.cloudinaryService = cloudinaryService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void init() {
        if (portfolio) {
            populateNoticias();
            populateAnuncios();
            createUsuario();
            logger.info("Database do Portfólio inicializada com sucesso!");
        }
    }

    private void populateNoticias() {
        if (noticiaService.getNoticiaById(1L).isPresent()) {
            return;
        }

        logger.info("Adicionando as notícias ao Portfólio");
        try {
            cloudinaryService.clearCloudinaryFolder();

            noticiaService.cadastrarNoticia(
                    Type.HIGHLIGHT,
                    Category.GERAL,
                    "Como publicar uma Notícia ou Anúncio em seu site?",
                    "Nesta matéria você irá descobrir como publicar notícias e anúncios em seu novo Portal!",
                    "Descubra como publicar notícias e anúncios personalizados em seu novo Portal, utilizando a tecnologia Bossa Web Solutions!",
                    getFileFromResources("noticia0_bws_como.jpg"),
                    "Após fazer **login** de administrador em seu portal, você poderá publicar suas notícias personalizadas e os anúncios vendidos! A Bossa Web Solutions criou uma solução prática para você, chamada **Área de Gerenciamento**! Confira o passo a passo para acessá-la: \n\n**1-** Clique no **rodapé** da página em **Portal de Notícias** (Você será redirecionado para a **página de login**). \n**2-** Efetue login e senha com as credencias fornecidas (para este portfólio são: **login: admin**, **senha: admin123**). \n**3-** Na seção de **Gerenciamento**, você poderá publicar suas notícias e anúncios vendidos de acordo com suas preferencias. \n\nVocê configura suas notícias como quiser. Se são destaques ou não, a categoria, informações, imagem. Tudo personalizado, do jeito que você quiser, a Bossa Web Solutions entrega! \n\nVocê pode personalizar totalmente seus anúncios, colocando data de expiração, a posição em que aparecem na página, gerando margem para negociação e o tempo em que ele será veiculado!\n\n**Bossa Web Solutions, Transforme o complexo em Simplicidade!**"
            );

            noticiaService.cadastrarNoticia(
                    Type.HIGHLIGHT,
                    Category.GERAL,
                    "Novidade! Um Novo Site de Notícias para você!",
                    "Neste novo Portal você encontrará tudo que precisa para se manter bem informado na Região.",
                    "Descubra como um site, um sistema e um app podem transformar sua empresa com a ajuda da Bossa Web Solutions, criando soluções digitais para um mundo cada vez mais conectado.",
                    getFileFromResources("bws_logo.png"),
                    "No mundo atual, ter uma **Identidade Digital** é essencial para empresas de qualquer segmento. Um site bem desenvolvido é a porta de entrada para clientes, parceiros e investidores, transmitindo profissionalismo, credibilidade e reforçando sua marca no mercado. A **Bossa Web Solutions** cria sites que não apenas informam, mas também engajam e conectam sua empresa ao público-alvo.\n\nAlém disso, sistemas personalizados são a chave para transformar o **Complexo em Simplicidade**. Automação de processos, gestão eficiente e acessibilidade são pilares que um sistema bem planejado oferece. Nossa equipe trabalha para que a tecnologia simplifique o dia a dia da sua empresa, permitindo que você foque no que realmente importa: crescer e inovar.\n\nPor fim, um aplicativo representa uma **Solução personalizada na palma de sua mão**. Ele conecta seus serviços diretamente ao cliente, criando uma experiência prática, moderna e acessível a qualquer momento."
            );

            noticiaService.cadastrarNoticia(
                    Type.HIGHLIGHT,
                    Category.GERAL,
                    "Novidades do Mundo da Tecnologia",
                    "Tudo o que você precisa saber para estar no mundo digital.",
                    "Descubra como um site, um sistema e um app podem transformar sua empresa com a ajuda da Bossa Web Solutions, criando soluções digitais para um mundo cada vez mais conectado.",
                    getFileFromResources("noticia1_bws.jpeg"),
                    "No mundo atual, ter uma **Identidade Digital** é essencial para empresas de qualquer segmento. Um site bem desenvolvido é a porta de entrada para clientes, parceiros e investidores, transmitindo profissionalismo, credibilidade e reforçando sua marca no mercado. A **Bossa Web Solutions** cria sites que não apenas informam, mas também engajam e conectam sua empresa ao público-alvo.\n\nAlém disso, sistemas personalizados são a chave para transformar o **Complexo em Simplicidade**. Automação de processos, gestão eficiente e acessibilidade são pilares que um sistema bem planejado oferece."
            );

            noticiaService.cadastrarNoticia(
                    Type.HIGHLIGHT,
                    Category.GERAL,
                    "Identidade Digital: Tenha seu site hoje mesmo",
                    "Seu site profissional e funcional para destacar sua presença online.",
                    "Oferecemos soluções personalizadas para sites responsivos, com design moderno e performance otimizada.",
                    getFileFromResources("noticia2_bws_site.jpeg"),
                    "Na **Bossa Web Solutions**, nosso objetivo é ajudar você a construir uma **Identidade Digital** sólida e impactante. Criamos sites que atendem às necessidades dos nossos clientes, garantindo qualidade e inovação."
            );

            noticiaService.cadastrarNoticia(
                    Type.HIGHLIGHT,
                    Category.GERAL,
                    "Transforme o complexo em simplicidade com nossos sistemas",
                    "Automatize processos e alcance máxima eficiência.",
                    "Criamos sistemas personalizados para atender às demandas do seu negócio, simplificando operações complexas.",
                    getFileFromResources("noticia3_bws_sistema.jpeg"),
                    "Na **Bossa Web Solutions**, oferecemos sistemas sob medida que ajudam sua empresa a otimizar processos, reduzir custos e aumentar a produtividade."
            );

            noticiaService.cadastrarNoticia(
                    Type.HIGHLIGHT,
                    Category.GERAL,
                    "Solução personalizada na palma de sua mão: Aplicativos que conectam você ao futuro",
                    "Tenha tecnologia na palma da mão com apps modernos e intuitivos.",
                    "Desenvolvemos aplicativos personalizados para Android e iOS, com foco na experiência do usuário.",
                    getFileFromResources("noticia4_bws_app.jpeg"),
                    "Nossos aplicativos oferecem soluções inovadoras para conectar sua ideia ao público-alvo, com interfaces intuitivas e design responsivo."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.GERAL,
                    "Aumento da temperatura média no Brasil",
                    "Estudo revela aumento significativo nos últimos anos.",
                    "Um novo estudo indica que a temperatura média no Brasil tem aumentado a uma taxa alarmante, afetando o clima e a agricultura.",
                    getFileFromResources("noticia5_calor.png"),
                    "De acordo com pesquisadores, a temperatura média no Brasil aumentou em 1,5 graus Celsius nos últimos 50 anos..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.POLITICA,
                    "Novo projeto de lei sobre segurança pública é aprovado",
                    "Legislação visa fortalecer medidas de segurança nas cidades.",
                    "A nova legislação aprovada pelo Congresso Nacional promete aumentar o investimento em segurança pública e combater a criminalidade.",
                    getFileFromResources("noticia6_lei.jpg"),
                    "Após intensos debates, o Congresso Nacional aprovou um novo projeto de lei que busca fortalecer a segurança nas cidades brasileiras..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.POLICIAL,
                    "Operação policial desmantela quadrilha de tráfico",
                    "Ação conjunta entre diversas forças de segurança resulta em prisões.",
                    "Uma operação realizada em várias cidades do país desarticulou uma quadrilha responsável pelo tráfico de drogas.",
                    getFileFromResources("noticia7_policia.jpg"),
                    "A operação contou com a participação da Polícia Federal, Polícia Militar e outras agências de segurança..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.ESPORTES,
                    "Time local vence campeonato regional",
                    "Vitória emocionante leva equipe ao torneio nacional.",
                    "Após um campeonato disputado, o time local se destacou e conquistou o primeiro lugar.",
                    getFileFromResources("noticia8_futebol.jpg"),
                    "O time local de futebol, após uma campanha emocionante, venceu o campeonato regional e garantiu sua vaga no torneio nacional..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.CULTURA,
                    "Festival de cinema ocorre na cidade",
                    "Evento traz produções nacionais e internacionais para o público.",
                    "O festival de cinema deste ano apresenta uma seleção diversificada de filmes para todos os gostos.",
                    getFileFromResources("noticia9_cinema.jpg"),
                    "O festival de cinema, que acontece anualmente, traz uma programação repleta de filmes independentes e grandes produções..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.GERAL,
                    "Crescimento do turismo em regiões rurais",
                    "Atrações rurais ganham destaque e atraem visitantes.",
                    "O turismo rural tem se mostrado uma alternativa viável para a economia em diversas regiões.",
                    getFileFromResources("noticia10_turismo.jpg"),
                    "Com a pandemia, muitos turistas passaram a buscar experiências em ambientes mais naturais..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.POLITICA,
                    "Eleições municipais se aproximam",
                    "Preparativos estão a todo vapor para as próximas eleições.",
                    "Candidatos e partidos começam a definir suas estratégias para a disputa.",
                    getFileFromResources("noticia11_eleicoes.jpg"),
                    "Com a proximidade das eleições municipais, os partidos políticos têm se mobilizado para apresentar seus candidatos e propostas..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.ESPORTES,
                    "Atleta local se destaca em competição internacional",
                    "Desempenho exemplar garante medalhas para o Brasil.",
                    "O atleta conquistou medalhas em diferentes modalidades, elevando o nome do país.",
                    getFileFromResources("noticia12_atleta.jpg"),
                    "O atleta local, com uma trajetória inspiradora, participou de uma competição internacional e trouxe para casa várias medalhas..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.CULTURA,
                    "Exposição de arte contemporânea atrai visitantes",
                    "Artistas locais e nacionais têm espaço garantido na mostra.",
                    "A nova exposição no museu promete instigar reflexões sobre arte e sociedade.",
                    getFileFromResources("noticia13_exposicao.jpg"),
                    "A exposição de arte contemporânea que acontece no museu local tem atraído um grande número de visitantes..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.GERAL,
                    "Avanços na tecnologia de energias renováveis",
                    "Novas inovações prometem tornar a energia mais acessível.",
                    "O setor de energias renováveis cresce rapidamente com novas tecnologias.",
                    getFileFromResources("noticia14_energia.jpg"),
                    "O setor de energias renováveis tem experimentado um crescimento exponencial nos últimos anos..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.POLITICA,
                    "Reforma tributária é debatida no Senado",
                    "Proposta visa simplificar o sistema e aumentar a arrecadação.",
                    "O debate sobre a reforma tributária promete impactos significativos na economia.",
                    getFileFromResources("noticia15_senado.png"),
                    "A reforma tributária em discussão no Senado busca simplificar o sistema e aumentar a arrecadação do governo..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.ESPORTES,
                    "Maratona da cidade atrai corredores de todo o país",
                    "Evento se tornou um dos maiores do calendário esportivo.",
                    "A maratona anual recebe atletas e entusiastas do esporte em busca de superação.",
                    getFileFromResources("noticia16_maratona.jpg"),
                    "A maratona da cidade, que acontece anualmente, se consolidou como um dos maiores eventos esportivos do calendário nacional..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.CULTURA,
                    "Festa tradicional resgata costumes locais",
                    "Evento celebra a cultura e as tradições da região.",
                    "A festa reúne moradores e visitantes em uma celebração cheia de música e dança.",
                    getFileFromResources("noticia17_festival.png"),
                    "A festa tradicional que acontece anualmente na cidade resgata os costumes e a cultura local..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.CULTURA,
                    "Novo programa de incentivo à leitura é lançado",
                    "Iniciativa busca promover a literatura entre jovens.",
                    "O programa oferece acesso a livros e atividades culturais em escolas.",
                    getFileFromResources("noticia18_leitura.jpg"),
                    "Um novo programa de incentivo à leitura foi lançado com o objetivo de promover a literatura entre os jovens..."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.GERAL,
                    "Nova praça é inaugurada no centro da cidade",
                    "Espaço conta com áreas verdes, pista de caminhada e playground para crianças.",
                    "A praça busca oferecer lazer, esporte e convivência para moradores e visitantes.",
                    getFileFromResources("noticia19_praca.jpg"),
                    "A prefeitura inaugurou neste fim de semana uma nova praça no centro da cidade, trazendo um espaço moderno e acessível para lazer e convivência..."
            );

        } catch (IOException e) {
            logger.error("Ocorreu um erro ao popular as notícias do Portfolio. Erro: {}", e.getMessage());
        }
    }


    private void populateAnuncios() {
        if (anuncioService.existsById(1L)) {
            return;
        }

        logger.info("Adicionando Anúncios ao Portfólio");

        LocalDate dataExpiracao = LocalDate.of(2030, 12, 31);

        try {
            anuncioService.cadastrarAnuncio(
                    "https://www.bossawebsolutions.com.br",
                    getFileFromResources("addBannerTop.png"),
                    AdPosition.MAIN_TOP,
                    dataExpiracao
            );

            anuncioService.cadastrarAnuncio(
                    "https://www.bossawebsolutions.com.br",
                    getFileFromResources("addBannerMiddle.png"),
                    AdPosition.MAIN_MIDDLE,
                    dataExpiracao
            );

            anuncioService.cadastrarAnuncio(
                    "https://www.bossawebsolutions.com.br",
                    getFileFromResources("anuncio_bws_1.jpeg"),
                    AdPosition.NEWS_RIGHT,
                    dataExpiracao
            );
        } catch (IOException e) {
            logger.error("Ocorreu um erro ao popular os anúncios do Portfolio. Erro: {}", e.getMessage());
        }
    }

    private void createUsuario() {
        if (usuarioService.retornaUsuario() == null) { //Cria usuario se nao houver nenhum
            logger.info("Criando usuário para o Sistema");

            Usuario usuario = new Usuario();
            usuario.setUsername("admin");
            passwordEncoder.encode("admin123");
            usuarioService.adicionarUsuario(usuario);
        }
    }

    private MultipartFile getFileFromResources(String name) throws IOException {
        ClassPathResource resource = new ClassPathResource("/assets/images/" + name);
        return new MultipartFile() {
            @Override
            public String getName() { return resource.getFilename(); }
            @Override
            public String getOriginalFilename() { return resource.getFilename(); }
            @Override
            public String getContentType() { return "image/png"; }
            @SneakyThrows
            @Override
            public boolean isEmpty() { return resource.contentLength() == 0; }
            @SneakyThrows
            @Override
            public long getSize() { return resource.contentLength(); }
            @Override
            public byte[] getBytes() throws IOException { return resource.getInputStream().readAllBytes(); }
            @Override
            public InputStream getInputStream() throws IOException { return resource.getInputStream(); }
            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.copy(resource.getInputStream(), dest.toPath());
            }
        };
    }

}
