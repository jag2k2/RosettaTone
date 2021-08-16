package instrument.keybinder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharConverterTest {

    @Test
    void canConvertOctaveTwo(){
        assertEquals(36, CharConverter.toKey('q'));
        assertEquals(37, CharConverter.toKey('w'));
        assertEquals(38, CharConverter.toKey('e'));
        assertEquals(39, CharConverter.toKey('r'));
        assertEquals(40, CharConverter.toKey('t'));
        assertEquals(41, CharConverter.toKey('y'));
        assertEquals(42, CharConverter.toKey('u'));
        assertEquals(43, CharConverter.toKey('i'));
        assertEquals(44, CharConverter.toKey('o'));
        assertEquals(45, CharConverter.toKey('p'));
        assertEquals(46, CharConverter.toKey('['));
        assertEquals(47, CharConverter.toKey(']'));
    }

    @Test
    void canConvertOctaveThree(){
        assertEquals(48, CharConverter.toKey('1'));
        assertEquals(49, CharConverter.toKey('2'));
        assertEquals(50, CharConverter.toKey('3'));
        assertEquals(51, CharConverter.toKey('4'));
        assertEquals(52, CharConverter.toKey('5'));
        assertEquals(53, CharConverter.toKey('6'));
        assertEquals(54, CharConverter.toKey('7'));
        assertEquals(55, CharConverter.toKey('8'));
        assertEquals(56, CharConverter.toKey('9'));
        assertEquals(57, CharConverter.toKey('0'));
        assertEquals(58, CharConverter.toKey('-'));
        assertEquals(59, CharConverter.toKey('='));
    }

    @Test
    void canConvertOctaveFour(){
        assertEquals(60, CharConverter.toKey('Q'));
        assertEquals(61, CharConverter.toKey('W'));
        assertEquals(62, CharConverter.toKey('E'));
        assertEquals(63, CharConverter.toKey('R'));
        assertEquals(64, CharConverter.toKey('T'));
        assertEquals(65, CharConverter.toKey('Y'));
        assertEquals(66, CharConverter.toKey('U'));
        assertEquals(67, CharConverter.toKey('I'));
        assertEquals(68, CharConverter.toKey('O'));
        assertEquals(69, CharConverter.toKey('P'));
        assertEquals(70, CharConverter.toKey('{'));
        assertEquals(71, CharConverter.toKey('}'));
    }

    @Test
    void canConvertOctaveFive(){
        assertEquals(72, CharConverter.toKey('!'));
        assertEquals(73, CharConverter.toKey('@'));
        assertEquals(74, CharConverter.toKey('#'));
        assertEquals(75, CharConverter.toKey('$'));
        assertEquals(76, CharConverter.toKey('%'));
        assertEquals(77, CharConverter.toKey('^'));
        assertEquals(78, CharConverter.toKey('&'));
        assertEquals(79, CharConverter.toKey('*'));
        assertEquals(80, CharConverter.toKey('('));
        assertEquals(81, CharConverter.toKey(')'));
        assertEquals(82, CharConverter.toKey('_'));
        assertEquals(83, CharConverter.toKey('+'));
    }

}