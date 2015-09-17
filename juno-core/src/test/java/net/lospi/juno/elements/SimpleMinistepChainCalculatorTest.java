package net.lospi.juno.elements;

import com.google.common.collect.ImmutableList;
import org.javatuples.Pair;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class SimpleMinistepChainCalculatorTest {
    private List<ActorAspect> actorAspects;
    private State state;
    private Link link1, link2;
    private ChainSegmentBuilder chainSegmentBuilder;

    @BeforeMethod
    public void setUp(){
        link1 = mock(Link.class);
        link2 = mock(Link.class);
        actorAspects = mock(List.class);
        state = mock(State.class);
        chainSegmentBuilder = mock(ChainSegmentBuilder.class);
    }

    public void testGetCcps() throws Exception {
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        ActorAspect actorAspect1 = new ActorAspect("actor1", "aspect1");
        ActorAspect actorAspect2 = new ActorAspect("actor2", "aspect1");
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        // 1,2 = {0,3}     (0,3)
        // 1,3 = {1,5,8}   (1,5) (5,8)
        // 2,1 = {2,7}
        // 2,3 = {}
        // 3,1 = {}
        // 3,2 = {}
        chain.append(new Ministep(actorAspect1, "actor2")); // 12
        chain.append(new Ministep(actorAspect1, "actor3")); // 13
        chain.append(new Ministep(actorAspect2, "actor1")); // 21
        chain.append(new Ministep(actorAspect1, "actor2")); // 12
        chain.append(new Ministep(actorAspect1, "actor1")); // 11
        chain.append(new Ministep(actorAspect1, "actor3")); // 13
        chain.append(new Ministep(actorAspect2, "actor2")); // 22
        chain.append(new Ministep(actorAspect2, "actor1")); // 21
        chain.append(new Ministep(actorAspect1, "actor3")); // 13
        List<Pair<Integer, Integer>> result = calculator.getConsecutivelyCancelingPairs(chain, 2, 3);
        assertThat(result).containsOnly(Pair.with(0, 3), Pair.with(1, 5), Pair.with(5, 8));
    }


    public void testGetCcpsOverMaxSegmentLength() throws Exception {
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        ActorAspect actorAspect1 = new ActorAspect("actor1", "aspect1");
        ActorAspect actorAspect2 = new ActorAspect("actor2", "aspect1");
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        // 1,2 = {0,3}     (0,3)
        // 1,3 = {1,5,8}   (1,5) (5,8)
        // 2,1 = {2,7}
        // 2,3 = {}
        // 3,1 = {}
        // 3,2 = {}
        chain.append(new Ministep(actorAspect1, "actor2")); // 12
        chain.append(new Ministep(actorAspect1, "actor3")); // 13
        chain.append(new Ministep(actorAspect2, "actor1")); // 21
        chain.append(new Ministep(actorAspect1, "actor2")); // 12
        chain.append(new Ministep(actorAspect1, "actor2")); // 12
        chain.append(new Ministep(actorAspect1, "actor1")); // 11
        chain.append(new Ministep(actorAspect1, "actor3")); // 13
        chain.append(new Ministep(actorAspect2, "actor2")); // 22
        chain.append(new Ministep(actorAspect2, "actor1")); // 21
        chain.append(new Ministep(actorAspect1, "actor3")); // 13
        List<Pair<Integer, Integer>> result = calculator.getConsecutivelyCancelingPairs(chain, 2, 3);
        assertThat(result).containsOnly(Pair.with(0, 3), Pair.with(6, 9));
    }

    public void testGetCcpsWithNonMinistepsPresent() throws Exception {
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        ActorAspect actorAspect1 = new ActorAspect("actor1", "aspect1");
        ActorAspect actorAspect2 = new ActorAspect("actor2", "aspect1");
        Link nonMinistep = mock(Link.class);
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        // 1,2 = {0,3}     (0,3)
        // 1,3 = {1,5,8}   (1,5) (5,8)
        // 2,1 = {2,7}
        // 2,3 = {}
        // 3,1 = {}
        // 3,2 = {}
        chain.append(nonMinistep);
        chain.append(new Ministep(actorAspect1, "actor2")); // 12
        chain.append(new Ministep(actorAspect1, "actor3")); // 13
        chain.append(new Ministep(actorAspect2, "actor1")); // 21
        chain.append(new Ministep(actorAspect1, "actor2")); // 12
        chain.append(new Ministep(actorAspect1, "actor1")); // 11
        chain.append(new Ministep(actorAspect1, "actor3")); // 13
        chain.append(new Ministep(actorAspect2, "actor2")); // 22
        chain.append(new Ministep(actorAspect2, "actor1")); // 21
        chain.append(new Ministep(actorAspect1, "actor3")); // 13
        chain.append(nonMinistep);
        List<Pair<Integer, Integer>> result = calculator.getConsecutivelyCancelingPairs(chain, 2, 3);
        assertThat(result).containsOnly(Pair.with(1, 4), Pair.with(2, 6), Pair.with(6, 9));
    }


    public void testGetCcpCount() throws Exception {
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        ActorAspect actorAspect1 = new ActorAspect("actor1", "aspect1");
        ActorAspect actorAspect2 = new ActorAspect("actor2", "aspect1");
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        // 1,2 = {0,3}     (0,3)
        // 1,3 = {1,5,8}   (1,5) (5,8)
        // 2,1 = {2,7}
        // 2,3 = {}
        // 3,1 = {}
        // 3,2 = {}
        chain.append(new Ministep(actorAspect1, "actor2")); // 12
        chain.append(new Ministep(actorAspect1, "actor3")); // 13
        chain.append(new Ministep(actorAspect2, "actor1")); // 21
        chain.append(new Ministep(actorAspect1, "actor2")); // 12
        chain.append(new Ministep(actorAspect1, "actor1")); // 11
        chain.append(new Ministep(actorAspect1, "actor3")); // 13
        chain.append(new Ministep(actorAspect2, "actor2")); // 22
        chain.append(new Ministep(actorAspect2, "actor1")); // 21
        chain.append(new Ministep(actorAspect1, "actor3")); // 13
        int result = calculator.getConsecutivelyCancellingPairsCount(chain, 2, 3);
        assertThat(result).isEqualTo(3);
    }

    public void indexOfNextMatchingLinkAfter(){
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1); //0
        chain.append(link1); //1
        chain.append(link2); //2
        chain.append(link2); //3
        chain.append(link1); //4
        chain.append(link1); //5
        Integer result = calculator.indexOfNextMatchingLinkAfter(chain, 1, link1);
        assertThat(result).isEqualTo(4);
    }

    public void indexOfNextMatchingLinkAfterNoMatch(){
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link1);
        chain.append(link2);
        chain.append(link2);
        Integer result = calculator.indexOfNextMatchingLinkAfter(chain, 1, link1);
        assertThat(result).isNull();
    }

    public void indexOfSecondMatchingLinkAfter(){
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link1);
        chain.append(link2);
        chain.append(link2);
        chain.append(link1);
        chain.append(link1);
        chain.append(link2);
        Integer result = calculator.indexOfSecondMatchingLinkAfter(chain, 1, link1);
        assertThat(result).isEqualTo(5);
    }


    public void getAlterCountFor(){
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        actorAspects = ImmutableList.of(new ActorAspect("actor0", "aspect1"),
                new ActorAspect("actor1", "aspect1"),
                new ActorAspect("actor2", "aspect2"),
                new ActorAspect("actor3", "aspect2"),
                new ActorAspect("actor4", "aspect2"),
                new ActorAspect("actor5", "aspect3"));
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        int result = calculator.getAlterCountFor(chain, new ActorAspect("actor3", "aspect2"));
        assertThat(result).isEqualTo(2);
    }


    public void indexOfSecondMatchingLinkAfterOneMatch(){
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        Link link1 = mock(Link.class);
        Link link2 = mock(Link.class);
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link1);
        chain.append(link2);
        chain.append(link2);
        chain.append(link1);
        chain.append(link2);
        Integer result = calculator.indexOfSecondMatchingLinkAfter(chain, 1, link1);
        assertThat(result).isNull();
    }

    public void indexOfSecondMatchingLinkAfterNoMatch(){
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link1);
        chain.append(link2);
        chain.append(link2);
        chain.append(link2);
        chain.append(link2);
        Integer result = calculator.indexOfSecondMatchingLinkAfter(chain, 1, link1);
        assertThat(result).isNull();
    }

    public void testGetDiagonalLinksCount() throws Exception {
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        Link diagonalLink = new Ministep(new ActorAspect("ego", "aspect"), "ego");
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        assertThat(calculator.getDiagonalLinksCount(chain)).isZero();
        chain.insertAt(0, diagonalLink);
        assertThat(calculator.getDiagonalLinksCount(chain)).isEqualTo(1);
        chain.insertAt(0, diagonalLink);
        assertThat(calculator.getDiagonalLinksCount(chain)).isEqualTo(2);
        chain.deleteAt(1);
        assertThat(calculator.getDiagonalLinksCount(chain)).isEqualTo(1);
        chain.deleteAt(0);
        assertThat(calculator.getDiagonalLinksCount(chain)).isZero();
    }

    public void testGetDiagonalLinksCountWithNonMinisteps() throws Exception {
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        Link diagonalLink = new Ministep(new ActorAspect("ego", "aspect"), "ego");
        Link nonMinistep = mock(Link.class);
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);

        assertThat(calculator.getDiagonalLinksCount(chain)).isZero();
        chain.insertAt(0, diagonalLink);
        chain.insertAt(0, nonMinistep);
        assertThat(calculator.getDiagonalLinksCount(chain)).isEqualTo(1);
        chain.insertAt(0, diagonalLink);
        chain.insertAt(0, nonMinistep);
        assertThat(calculator.getDiagonalLinksCount(chain)).isEqualTo(2);
        chain.deleteAt(1);
        chain.insertAt(0, nonMinistep);
        assertThat(calculator.getDiagonalLinksCount(chain)).isEqualTo(1);
        chain.insertAt(0, nonMinistep);
        chain.deleteAt(4);
        assertThat(calculator.getDiagonalLinksCount(chain)).isZero();
    }

    public void getAltersFor() throws Exception {
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        ActorAspect actorAspect1 = new ActorAspect("actor1", "a1");
        ActorAspect actorAspect2 = new ActorAspect("actor2", "a1");
        ActorAspect actorAspect3 = new ActorAspect("actor3", "a1");
        ActorAspect actorAspect4 = new ActorAspect("actor4", "a2");
        actorAspects = ImmutableList.of(actorAspect1, actorAspect2, actorAspect3, actorAspect4);
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        assertThat(calculator.getAltersFor(chain, actorAspect2)).isEqualTo(ImmutableList.of("actor1", "actor3"));
    }

    public void testGetDiagonalLinkIndices() throws Exception {
        SimpleMinistepChainCalculator calculator = new SimpleMinistepChainCalculator();
        Link diagonalLink = new Ministep(new ActorAspect("ego", "aspect"), "ego");
        Link nondiagonalLink = new Ministep(new ActorAspect("ego", "aspect"), "alter");
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        assertThat(calculator.getDiagonalLinkIndices(chain)).isEmpty();
        chain.append(diagonalLink);
        assertThat(calculator.getDiagonalLinkIndices(chain)).containsOnly(0);
        chain.append(diagonalLink);
        assertThat(calculator.getDiagonalLinkIndices(chain)).containsOnly(0, 1);
        chain.append(nondiagonalLink);
        assertThat(calculator.getDiagonalLinkIndices(chain)).containsOnly(0, 1);
        chain.deleteAt(1);
        assertThat(calculator.getDiagonalLinkIndices(chain)).containsOnly(0);
        chain.deleteAt(0);
        chain.deleteAt(0);
        assertThat(calculator.getDiagonalLinkIndices(chain)).isEmpty();
    }
}