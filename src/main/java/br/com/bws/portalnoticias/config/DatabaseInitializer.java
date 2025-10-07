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
                    "Após fazer **login** de administrador em seu portal, você poderá publicar suas notícias personalizadas e os anúncios vendidos! A Bossa Web Solutions criou uma solução prática para você, chamada **Área de Gerenciamento**! Confira o passo a passo para acessá-la: \\n\\n**1-** Clique no **rodapé** da página em **Portal de Notícias** (Você será redirecionado para a **página de login**). \\n**2-** Efetue login e senha com as credencias fornecidas (para este portfólio são: **login: admin**, **senha: admin123**). \\n**3-** Na seção de **Gerenciamento**, você poderá publicar suas notícias e anúncios vendidos de acordo com suas preferencias. \\n\\nVocê configura suas notícias como quiser. Se são destaques ou não, a categoria, informações, imagem. Tudo personalizado, do jeito que você quiser, a Bossa Web Solutions entrega! \\n\\nVocê pode personalizar totalmente seus anúncios, colocando data de expiração, a posição em que aparecem na página, gerando margem para negociação e o tempo em que ele será veiculado!\\n\\n**Bossa Web Solutions, Transforme o complexo em Simplicidade!**"
            );

            noticiaService.cadastrarNoticia(
                    Type.HIGHLIGHT,
                    Category.GERAL,
                    "Novidade! Um Novo Site de Notícias para você!",
                    "Neste novo Portal você encontrará tudo que precisa para se manter bem informado na Região.",
                    "Descubra como um site, um sistema e um app podem transformar sua empresa com a ajuda da Bossa Web Solutions, criando soluções digitais para um mundo cada vez mais conectado.",
                    getFileFromResources("bws_logo.png"),
                    "No mundo atual, ter uma **Identidade Digital** é essencial para empresas de qualquer segmento. Um site bem desenvolvido é a porta de entrada para clientes, parceiros e investidores, transmitindo profissionalismo, credibilidade e reforçando sua marca no mercado. A **Bossa Web Solutions** cria sites que não apenas informam, mas também engajam e conectam sua empresa ao público-alvo.\\n\\nAlém disso, sistemas personalizados são a chave para transformar o **Complexo em Simplicidade**. Automação de processos, gestão eficiente e acessibilidade são pilares que um sistema bem planejado oferece. Na **Bossa Web Solutions**, acreditamos que a tecnologia deve simplificar o dia a dia da sua empresa, permitindo que você foque no que realmente importa: crescer e inovar.\\n\\nPor fim, um aplicativo representa uma **Solução personalizada na palma de sua mão**. Ele conecta seus serviços diretamente ao cliente, criando uma experiência prática, moderna e acessível a qualquer momento. Seja para fidelizar seu público ou facilitar interações, a **Bossa Web Solutions** está pronta para desenvolver o app ideal que irá diferenciar sua marca no mercado."
            );

            noticiaService.cadastrarNoticia(
                    Type.HIGHLIGHT,
                    Category.GERAL,
                    "Novidades do Mundo da Tecnologia",
                    "Tudo o que você precisa saber para estar no mundo digital.",
                    "Descubra como um site, um sistema e um app podem transformar sua empresa com a ajuda da Bossa Web Solutions, criando soluções digitais para um mundo cada vez mais conectado.",
                    getFileFromResources("noticia1_bws.jpeg"),
                    "No mundo atual, ter uma **Identidade Digital** é essencial para empresas de qualquer segmento. Um site bem desenvolvido é a porta de entrada para clientes, parceiros e investidores, transmitindo profissionalismo, credibilidade e reforçando sua marca no mercado. A **Bossa Web Solutions** cria sites que não apenas informam, mas também engajam e conectam sua empresa ao público-alvo.\\n\\nAlém disso, sistemas personalizados são a chave para transformar o **Complexo em Simplicidade**. Automação de processos, gestão eficiente e acessibilidade são pilares que um sistema bem planejado oferece. Na **Bossa Web Solutions**, acreditamos que a tecnologia deve simplificar o dia a dia da sua empresa, permitindo que você foque no que realmente importa: crescer e inovar.\\n\\nPor fim, um aplicativo representa uma **Solução personalizada na palma de sua mão**. Ele conecta seus serviços diretamente ao cliente, criando uma experiência prática, moderna e acessível a qualquer momento. Seja para fidelizar seu público ou facilitar interações, a **Bossa Web Solutions** está pronta para desenvolver o app ideal que irá diferenciar sua marca no mercado."
            );

            noticiaService.cadastrarNoticia(
                    Type.HIGHLIGHT,
                    Category.GERAL,
                    "Identidade Digital: Tenha seu site hoje mesmo",
                    "Seu site profissional e funcional para destacar sua presença online.",
                    "Oferecemos soluções personalizadas para sites responsivos, com design moderno e performance otimizada.",
                    getFileFromResources("noticia2_bws_site.jpeg"),
                    "Na **Bossa Web Solutions**, nosso objetivo é ajudar você a construir uma **Identidade Digital** sólida e impactante. Criamos sites que atendem às necessidades dos nossos clientes, desde páginas institucionais até lojas virtuais, garantindo qualidade e inovação. Nossos projetos são desenvolvidos com foco na experiência do usuário, proporcionando interfaces intuitivas e responsivas.\\n\\nAlém disso, oferecemos suporte contínuo para que você possa gerenciar seu site com facilidade e eficácia. Nossa equipe está sempre disponível para ajudar com atualizações e melhorias, assegurando que sua presença online esteja sempre atualizada e alinhada com as tendências do mercado."
            );

            noticiaService.cadastrarNoticia(
                    Type.HIGHLIGHT,
                    Category.GERAL,
                    "Transforme o complexo em simplicidade com nossos sistemas",
                    "Automatize processos e alcance máxima eficiência.",
                    "Criamos sistemas personalizados para atender às demandas do seu negócio, simplificando operações complexas.",
                    getFileFromResources("noticia3_bws_sistema.jpeg"),
                    "Na **Bossa Web Solutions**, oferecemos sistemas sob medida que ajudam sua empresa a otimizar processos, reduzir custos e aumentar a produtividade. Nossa abordagem é focada nas necessidades do cliente, permitindo que cada sistema seja adaptado para resolver os desafios específicos do seu negócio.\\n\\nTrabalhamos com tecnologias de ponta e práticas de desenvolvimento ágil, garantindo que nossos sistemas sejam robustos, seguros e fáceis de usar. Nossa equipe de suporte está sempre pronta para garantir que suas operações ocorram sem problemas, permitindo que você se concentre no que realmente importa: crescer sua empresa."
            );

            noticiaService.cadastrarNoticia(
                    Type.HIGHLIGHT,
                    Category.GERAL,
                    "Solução personalizada na palma de sua mão: Aplicativos que conectam você ao futuro",
                    "Tenha tecnologia na palma da mão com apps modernos e intuitivos.",
                    "Desenvolvemos aplicativos personalizados para Android e iOS, com foco na experiência do usuário.",
                    getFileFromResources("noticia4_bws_app.jpeg"),
                    "Na **Bossa Web Solutions**, nossos aplicativos oferecem soluções inovadoras para conectar sua ideia ao público-alvo. Trabalhamos com interfaces intuitivas e design responsivo para garantir a melhor experiência possível. Cada aplicativo é projetado para ser altamente funcional e esteticamente agradável, atendendo às expectativas dos usuários modernos.\\n\\nAlém disso, priorizamos a performance e a segurança, garantindo que suas informações e as dos seus usuários estejam sempre protegidas. Nossa equipe de desenvolvedores trabalha em estreita colaboração com você para entender suas necessidades e traduzir suas ideias em soluções práticas e eficazes, colocando a **Solução na palma de sua mão.**"
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.GERAL,
                    "Aumento da temperatura média no Brasil",
                    "Estudo revela aumento significativo nos últimos anos.",
                    "Um novo estudo indica que a temperatura média no Brasil tem aumentado a uma taxa alarmante, afetando o clima e a agricultura.",
                    getFileFromResources("noticia5_calor.png"),
                    "De acordo com pesquisadores, a temperatura média no Brasil aumentou em 1,5 graus Celsius nos últimos 50 anos. Isso tem provocado mudanças significativas no clima, como secas mais prolongadas e chuvas intensas em diferentes regiões. Os impactos na agricultura são profundos, com safras sendo afetadas e produtores enfrentando dificuldades. Especialistas alertam que é necessário implementar políticas de sustentabilidade que considerem as novas realidades climáticas, promovendo uma gestão eficiente dos recursos hídricos e técnicas agrícolas mais resilientes.\\n\\nAlém disso, o aumento da temperatura também pode afetar a biodiversidade do país. Espécies nativas estão sob ameaça, com habitats sendo alterados. As autoridades precisam agir rapidamente para proteger ecossistemas vulneráveis, garantindo a preservação da fauna e flora brasileiras em um cenário de mudanças climáticas aceleradas."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.POLITICA,
                    "Novo projeto de lei sobre segurança pública é aprovado",
                    "Legislação visa fortalecer medidas de segurança nas cidades.",
                    "A nova legislação aprovada pelo Congresso Nacional promete aumentar o investimento em segurança pública e combater a criminalidade.",
                    getFileFromResources("noticia6_lei.jpg"),
                    "Após intensos debates, o Congresso Nacional aprovou um novo projeto de lei que busca fortalecer a segurança nas cidades brasileiras. A nova legislação prevê o aumento de recursos para as polícias e a implementação de programas de prevenção ao crime, visando a redução da violência. Especialistas em segurança pública acreditam que este é um passo crucial para enfrentar a crescente criminalidade em várias regiões do país.\\n\\nOs detalhes do projeto incluem também a criação de parcerias entre a sociedade civil e as forças de segurança, promovendo um modelo de segurança mais comunitário. As autoridades esperam que essas novas medidas ajudem a restaurar a confiança da população nas instituições e, ao mesmo tempo, ofereçam uma resposta eficaz às necessidades de segurança dos cidadãos. A implementação dessas políticas será acompanhada de perto por representantes da sociedade e da mídia, a fim de garantir transparência e eficácia."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.POLICIAL,
                    "Operação policial desmantela quadrilha de tráfico",
                    "Ação conjunta entre diversas forças de segurança resulta em prisões.",
                    "Uma operação realizada em várias cidades do país desarticulou uma quadrilha responsável pelo tráfico de drogas.",
                    getFileFromResources("noticia7_policia.jpg"),
                    "A operação contou com a participação da Polícia Federal, Polícia Militar e outras agências de segurança. Durante a ação, mais de 50 pessoas foram presas e toneladas de drogas foram apreendidas. A operação é considerada uma das maiores já realizadas no país para combater o tráfico, reunindo esforços significativos para desarticular uma rede criminosa que operava em diferentes estados.\\n\\nAs autoridades afirmam que a operação não apenas retirou grandes quantidades de drogas do mercado, mas também desmantelou a infraestrutura logística da quadrilha. Essa ação é um marco no combate ao tráfico de drogas no Brasil, e os resultados mostram o compromisso das forças de segurança em lidar com essa questão crítica. A população espera que essas operações continuem, levando a uma diminuição da violência associada ao tráfico nas comunidades."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.ESPORTES,
                    "Time local vence campeonato regional",
                    "Vitória emocionante leva equipe ao torneio nacional.",
                    "Após um campeonato disputado, o time local se destacou e conquistou o primeiro lugar.",
                    getFileFromResources("noticia8_futebol.jpg"),
                    "O time local de futebol, após uma campanha emocionante, venceu o campeonato regional e garantiu sua vaga no torneio nacional. O jogo final foi decidido nos pênaltis, e a torcida fez a festa no estádio. A equipe mostrou determinação e talento durante toda a competição, enfrentando adversários difíceis e superando desafios significativos ao longo do caminho.\\n\\nO treinador elogiou o empenho dos jogadores, destacando a importância do trabalho em equipe e da disciplina nos treinos. Esta vitória não é apenas um triunfo esportivo, mas um símbolo de esperança e união para a comunidade local. Os torcedores estão ansiosos para ver como o time se sairá no campeonato nacional, onde enfrentará algumas das melhores equipes do país, e prometem lotar o estádio em apoio à sua equipe."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.CULTURA,
                    "Festival de cinema ocorre na cidade",
                    "Evento traz produções nacionais e internacionais para o público.",
                    "O festival de cinema deste ano apresenta uma seleção diversificada de filmes para todos os gostos.",
                    getFileFromResources("noticia9_cinema.jpg"),
                    "O festival de cinema, que acontece anualmente, traz uma programação repleta de filmes independentes e grandes produções. Este ano, o evento conta com a presença de cineastas renomados e diversas atividades interativas para os participantes, incluindo workshops e painéis de discussão. O festival não só promove a sétima arte, mas também gera um espaço de debate sobre questões sociais e culturais atuais.\\n\\nOs organizadores esperam atrair um público diversificado, estimulando o interesse por produções que muitas vezes não chegam ao circuito comercial. A integração de atividades culturais, como debates e sessões de perguntas e respostas com os diretores, proporciona uma experiência enriquecedora para os amantes do cinema. O evento é uma oportunidade para a cidade se consolidar como um polo cultural e turístico, atraindo visitantes e impulsionando a economia local."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.GERAL,
                    "Crescimento do turismo em regiões rurais",
                    "Atrações rurais ganham destaque e atraem visitantes.",
                    "O turismo rural tem se mostrado uma alternativa viável para a economia em diversas regiões.",
                    getFileFromResources("noticia10_turismo.jpg"),
                    "Com a pandemia, muitos turistas passaram a buscar experiências em ambientes mais naturais. Isso resultou no crescimento do turismo rural, onde as comunidades locais têm oferecido atividades como passeios a cavalo, trilhas e a degustação de produtos típicos. A tendência é que esse tipo de turismo continue em alta nos próximos anos, à medida que mais pessoas buscam desconectar-se do estresse das cidades.\\n\\nEsse novo enfoque no turismo também tem trazido benefícios diretos para as economias locais, com o aumento da demanda por produtos artesanais e gastronômicos regionais. As comunidades estão se adaptando a essa nova realidade, investindo em infraestrutura e capacitação para oferecer experiências de qualidade. O turismo rural não apenas promove a preservação ambiental, mas também fortalece a identidade cultural das regiões, tornando-se uma ferramenta poderosa de desenvolvimento sustentável."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.POLITICA,
                    "Eleições municipais se aproximam",
                    "Preparativos estão a todo vapor para as próximas eleições.",
                    "Candidatos e partidos começam a definir suas estratégias para a disputa.",
                    getFileFromResources("noticia11_eleicoes.jpg"),
                    "Com a proximidade das eleições municipais, os partidos políticos têm se mobilizado para apresentar seus candidatos e propostas. Comícios, reuniões e campanhas nas redes sociais estão entre as estratégias adotadas. Especialistas apontam que as eleições deste ano podem ser as mais disputadas da história recente, com um aumento significativo no número de candidatos e partidos.\\n\\nAs discussões sobre temas importantes, como segurança, educação e saúde, têm ganhado destaque nas pautas eleitorais, com candidatos tentando se conectar com as preocupações da população. A participação dos jovens e a influência das redes sociais também estão moldando o cenário eleitoral, levando os partidos a adaptar suas mensagens e estratégias para alcançar um público mais amplo. A expectativa é que, com a mobilização da sociedade civil, a participação nas urnas também aumente, refletindo um maior engajamento político."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.ESPORTES,
                    "Atleta local se destaca em competição internacional",
                    "Desempenho exemplar garante medalhas para o Brasil.",
                    "O atleta conquistou medalhas em diferentes modalidades, elevando o nome do país.",
                    getFileFromResources("noticia12_atleta.jpg"),
                    "O atleta local, com uma trajetória inspiradora, participou de uma competição internacional e trouxe para casa várias medalhas. Seu desempenho nas provas de natação foi admirável, batendo recordes pessoais e conquistando a medalha de ouro em sua especialidade. Os treinadores e a comunidade local comemoram suas vitórias, vendo nele um exemplo de dedicação e superação.\\n\\nAlém das vitórias, o atleta tem se destacado por seu trabalho social, incentivando jovens a praticar esportes e a acreditar em seus sonhos. Ele frequentemente visita escolas e comunidades, compartilhando sua experiência e motivando outros a se envolverem em atividades físicas. Essa combinação de talento e comprometimento social torna o atleta uma figura admirada e respeitada, refletindo o potencial que o esporte pode ter para transformar vidas e unir comunidades."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.CULTURA,
                    "Exposição de arte contemporânea atrai visitantes",
                    "Artistas locais e nacionais têm espaço garantido na mostra.",
                    "A nova exposição no museu promete instigar reflexões sobre arte e sociedade.",
                    getFileFromResources("noticia13_exposicao.jpg"),
                    "A exposição de arte contemporânea que acontece no museu local tem atraído um grande número de visitantes. Com obras de artistas locais e nacionais, a mostra busca provocar reflexões sobre a sociedade e as questões contemporâneas. As instalações e performances são variadas, trazendo uma diversidade de estilos e abordagens que encantam o público.\\n\\nOs organizadores incentivam a interação entre os visitantes e as obras, promovendo debates e oficinas durante a exposição. A intenção é não apenas apresentar a arte, mas também tornar o espaço um ambiente de troca de ideias e experiências. A exposição tem se mostrado uma ótima oportunidade para promover a cultura e fortalecer a cena artística da região, contribuindo para a formação de novos públicos e a valorização da arte brasileira."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.GERAL,
                    "Avanços na tecnologia de energias renováveis",
                    "Novas inovações prometem tornar a energia mais acessível.",
                    "O setor de energias renováveis cresce rapidamente com novas tecnologias.",
                    getFileFromResources("noticia14_energia.jpg"),
                    "O setor de energias renováveis tem experimentado um crescimento exponencial nos últimos anos, impulsionado por inovações tecnológicas que prometem tornar a energia mais acessível e sustentável. Com a diminuição dos custos de produção e a melhoria na eficiência dos sistemas, mais empresas e residências estão adotando fontes de energia solar e eólica.\\n\\nAlém disso, a pesquisa e o desenvolvimento em armazenamento de energia têm avançado, permitindo que as energias renováveis se tornem uma solução viável para atender à demanda crescente por eletricidade. Governos e investidores estão cada vez mais cientes da importância de transitar para uma economia de baixo carbono, o que resulta em políticas e incentivos para a adoção de tecnologias limpas. A expectativa é que esses avanços não só ajudem na luta contra as mudanças climáticas, mas também promovam a criação de novos empregos e oportunidades econômicas."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.POLITICA,
                    "Reforma tributária é debatida no Senado",
                    "Proposta visa simplificar o sistema e aumentar a arrecadação.",
                    "O debate sobre a reforma tributária promete impactos significativos na economia.",
                    getFileFromResources("noticia15_senado.png"),
                    "A reforma tributária em discussão no Senado busca simplificar o sistema e aumentar a arrecadação do governo. Com a proposta, o objetivo é tornar a cobrança de impostos mais transparente e menos burocrática, beneficiando tanto os contribuintes quanto a administração pública. Especialistas em economia apontam que uma reforma bem-sucedida pode impulsionar o crescimento econômico e melhorar a competitividade das empresas brasileiras.\\n\\nNo entanto, o debate é acirrado, com diferentes grupos defendendo interesses variados. Enquanto alguns setores veem a reforma como uma oportunidade para reduzir a carga tributária, outros temem que mudanças possam impactar negativamente a arrecadação. O resultado desse debate pode moldar a política fiscal do país nos próximos anos, e a sociedade civil está atenta, pressionando por um sistema que promova a justiça tributária e o desenvolvimento econômico sustentável."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.ESPORTES,
                    "Maratona da cidade atrai corredores de todo o país",
                    "Evento se tornou um dos maiores do calendário esportivo.",
                    "A maratona anual recebe atletas e entusiastas do esporte em busca de superação.",
                    getFileFromResources("noticia16_maratona.jpg"),
                    "A maratona da cidade, que acontece anualmente, se consolidou como um dos maiores eventos esportivos do calendário nacional. Com a participação de corredores de todas as idades e níveis, a competição atrai atletas de todo o país em busca de superação e conquista pessoal. Este ano, a maratona conta com um percurso desafiador e uma estrutura de apoio que inclui hidratação e orientações para os participantes.\\n\\nAlém da corrida principal, o evento também oferece provas menores e atividades para a família, promovendo um ambiente festivo e inclusivo. Os organizadores destacam a importância do evento para a promoção da saúde e do bem-estar, incentivando a prática de esportes e um estilo de vida ativo. A expectativa é que a maratona continue a crescer, inspirando novos corredores e fortalecendo a comunidade esportiva local."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.CULTURA,
                    "Festa tradicional resgata costumes locais",
                    "Evento celebra a cultura e as tradições da região.",
                    "A festa reúne moradores e visitantes em uma celebração cheia de música e dança.",
                    getFileFromResources("noticia17_festival.png"),
                    "A festa tradicional que acontece anualmente na cidade resgata os costumes e a cultura local, reunindo moradores e visitantes em uma celebração vibrante. Com apresentações de danças típicas, comidas regionais e exposições de artesanato, o evento busca promover a identidade cultural da região. Os organizadores enfatizam a importância de manter vivas as tradições, especialmente em um mundo globalizado.\\n\\nAs atividades incluem concursos, oficinas de dança e música ao vivo, proporcionando um espaço para que as novas gerações conheçam e valorizem sua herança cultural. A festa é também uma oportunidade para fortalecer laços comunitários, com muitos participantes compartilhando suas histórias e experiências. O evento se tornou uma marca registrada da cidade, atraindo visitantes de longe e contribuindo para o turismo local."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.CULTURA,
                    "Novo programa de incentivo à leitura é lançado",
                    "Iniciativa busca promover a literatura entre jovens.",
                    "O programa oferece acesso a livros e atividades culturais em escolas.",
                    getFileFromResources("noticia18_leitura.jpg"),
                    "Um novo programa de incentivo à leitura foi lançado com o objetivo de promover a literatura entre os jovens. A iniciativa inclui a distribuição de livros em escolas e a realização de atividades culturais que incentivem a leitura e a escrita. Especialistas afirmam que a leitura é fundamental para o desenvolvimento cognitivo e social, e o programa visa proporcionar aos estudantes oportunidades para expandir seus horizontes literários.\\n\\nAs atividades incluem encontros com autores, oficinas de criação literária e clubes de leitura, criando um ambiente que estimula o interesse pela literatura. Os organizadores acreditam que essa é uma maneira eficaz de formar novos leitores e fomentar a cultura literária, além de contribuir para a formação de cidadãos mais críticos e engajados. A expectativa é que o programa seja um sucesso e inspire outras iniciativas em todo o país."
            );

            noticiaService.cadastrarNoticia(
                    Type.COMMON,
                    Category.GERAL,
                    "Nova praça é inaugurada no centro da cidade",
                    "Espaço conta com áreas verdes, pista de caminhada e playground para crianças.",
                    "A praça busca oferecer lazer, esporte e convivência para moradores e visitantes.",
                    getFileFromResources("noticia19_praca.jpg"),
                    "A prefeitura inaugurou neste fim de semana uma nova praça no centro da cidade, trazendo um espaço moderno e acessível para lazer e convivência. O local conta com pista de caminhada, playground para crianças, academia ao ar livre e áreas verdes com árvores nativas.\\n\\nDurante a cerimônia de abertura, autoridades destacaram a importância de investir em espaços públicos que promovam qualidade de vida e bem-estar. A comunidade marcou presença em peso e já começou a aproveitar as novas instalações, que também incluem bancos, wi-fi gratuito e iluminação sustentável. A expectativa é que a praça se torne um ponto de encontro para famílias, jovens e idosos, fortalecendo o sentimento de pertencimento e valorizando a vida urbana."
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
            usuario.setPassword(passwordEncoder.encode("YWRtaW4xMjM=")); //admin123 em encoded Base64
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
