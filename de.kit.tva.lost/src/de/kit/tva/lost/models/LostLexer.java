package de.kit.tva.lost.models;

import org.antlr.v4.runtime.CharStream;
// Generated from LOST.g4 by ANTLR 4.4
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class LostLexer extends Lexer {
    static {
	RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
    public static final int T__27 = 1, T__26 = 2, T__25 = 3, T__24 = 4, T__23 = 5, T__22 = 6, T__21 = 7, T__20 = 8,
	    T__19 = 9, T__18 = 10, T__17 = 11, T__16 = 12, T__15 = 13, T__14 = 14, T__13 = 15, T__12 = 16, T__11 = 17,
	    T__10 = 18, T__9 = 19, T__8 = 20, T__7 = 21, T__6 = 22, T__5 = 23, T__4 = 24, T__3 = 25, T__2 = 26,
	    T__1 = 27, T__0 = 28, WS = 29, NL = 30, OP = 31, ID = 32;
    public static String[] modeNames = { "DEFAULT_MODE" };

    public static final String[] tokenNames = { "'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'",
	    "'\\u0005'", "'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", "'\r'", "'\\u000E'",
	    "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", "'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'",
	    "'\\u0017'", "'\\u0018'", "'\\u0019'", "'\\u001A'", "'\\u001B'", "'\\u001C'", "'\\u001D'", "'\\u001E'",
	    "'\\u001F'", "' '" };
    public static final String[] ruleNames = { "T__27", "T__26", "T__25", "T__24", "T__23", "T__22", "T__21", "T__20",
	    "T__19", "T__18", "T__17", "T__16", "T__15", "T__14", "T__13", "T__12", "T__11", "T__10", "T__9", "T__8",
	    "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "WS", "NL", "OP", "ID" };

    public LostLexer(CharStream input) {
	super(input);
	_interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @Override
    public String getGrammarFileName() {
	return "LOST.g4";
    }

    @Override
    public String[] getTokenNames() {
	return tokenNames;
    }

    @Override
    public String[] getRuleNames() {
	return ruleNames;
    }

    @Override
    public String getSerializedATN() {
	return _serializedATN;
    }

    @Override
    public String[] getModeNames() {
	return modeNames;
    }

    @Override
    public ATN getATN() {
	return _ATN;
    }

    public static final String _serializedATN = "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\"\u00cd\b\1\4\2\t"
	    + "\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"
	    + "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
	    + "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
	    + "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"
	    + "\t!\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3"
	    + "\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t"
	    + "\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3"
	    + "\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3"
	    + "\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3"
	    + "\25\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3"
	    + "\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3"
	    + "\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\36\6"
	    + "\36\u00bc\n\36\r\36\16\36\u00bd\3\36\3\36\3\37\3\37\3 \6 \u00c5\n \r "
	    + "\16 \u00c6\3!\6!\u00ca\n!\r!\16!\u00cb\2\2\"\3\3\5\4\7\5\t\6\13\7\r\b"
	    + "\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26"
	    + "+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"\3\2\5\4\2\17\17\""
	    + "\"\6\2\'\',-/\61>@\5\2\62;C_c|\u00cf\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"
	    + "\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"
	    + "\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"
	    + "\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2"
	    + "\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3"
	    + "\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2"
	    + "\2\2\3C\3\2\2\2\5I\3\2\2\2\7O\3\2\2\2\tT\3\2\2\2\13V\3\2\2\2\rX\3\2\2"
	    + "\2\17a\3\2\2\2\21c\3\2\2\2\23f\3\2\2\2\25i\3\2\2\2\27l\3\2\2\2\31s\3\2"
	    + "\2\2\33v\3\2\2\2\35y\3\2\2\2\37|\3\2\2\2!~\3\2\2\2#\u0083\3\2\2\2%\u0085"
	    + "\3\2\2\2\'\u008a\3\2\2\2)\u008d\3\2\2\2+\u0092\3\2\2\2-\u0094\3\2\2\2"
	    + "/\u0097\3\2\2\2\61\u009a\3\2\2\2\63\u009f\3\2\2\2\65\u00a5\3\2\2\2\67"
	    + "\u00a7\3\2\2\29\u00b8\3\2\2\2;\u00bb\3\2\2\2=\u00c1\3\2\2\2?\u00c4\3\2"
	    + "\2\2A\u00c9\3\2\2\2CD\7r\2\2DE\7q\2\2EF\7u\2\2FG\7v\2\2GH\7<\2\2H\4\3"
	    + "\2\2\2IJ\7k\2\2JK\7p\2\2KL\7v\2\2LM\7o\2\2MN\7<\2\2N\6\3\2\2\2OP\7x\2"
	    + "\2PQ\7c\2\2QR\7t\2\2RS\7<\2\2S\b\3\2\2\2TU\7}\2\2U\n\3\2\2\2VW\7=\2\2"
	    + "W\f\3\2\2\2XY\7T\2\2YZ\7g\2\2Z[\7p\2\2[\\\7c\2\2\\]\7o\2\2]^\7k\2\2^_"
	    + "\7p\2\2_`\7i\2\2`\16\3\2\2\2ab\7\177\2\2b\20\3\2\2\2cd\7F\2\2de\7*\2\2"
	    + "e\22\3\2\2\2fg\7*\2\2gh\7+\2\2h\24\3\2\2\2ij\7T\2\2jk\7<\2\2k\26\3\2\2"
	    + "\2lm\7i\2\2mn\7w\2\2no\7c\2\2op\7t\2\2pq\7f\2\2qr\7<\2\2r\30\3\2\2\2s"
	    + "t\7E\2\2tu\7*\2\2u\32\3\2\2\2vw\7D\2\2wx\7*\2\2x\34\3\2\2\2yz\7Q\2\2z"
	    + "{\7<\2\2{\36\3\2\2\2|}\7*\2\2} \3\2\2\2~\177\7r\2\2\177\u0080\7t\2\2\u0080"
	    + "\u0081\7g\2\2\u0081\u0082\7<\2\2\u0082\"\3\2\2\2\u0083\u0084\7.\2\2\u0084"
	    + "$\3\2\2\2\u0085\u0086\7X\2\2\u0086\u0087\7c\2\2\u0087\u0088\7t\2\2\u0088"
	    + "\u0089\7u\2\2\u0089&\3\2\2\2\u008a\u008b\7O\2\2\u008b\u008c\7<\2\2\u008c"
	    + "(\3\2\2\2\u008d\u008e\7k\2\2\u008e\u008f\7p\2\2\u008f\u0090\7x\2\2\u0090"
	    + "\u0091\7<\2\2\u0091*\3\2\2\2\u0092\u0093\7\13\2\2\u0093,\3\2\2\2\u0094"
	    + "\u0095\7U\2\2\u0095\u0096\7*\2\2\u0096.\3\2\2\2\u0097\u0098\7N\2\2\u0098"
	    + "\u0099\7*\2\2\u0099\60\3\2\2\2\u009a\u009b\7u\2\2\u009b\u009c\7m\2\2\u009c"
	    + "\u009d\7k\2\2\u009d\u009e\7r\2\2\u009e\62\3\2\2\2\u009f\u00a0\7p\2\2\u00a0"
	    + "\u00a1\7c\2\2\u00a1\u00a2\7o\2\2\u00a2\u00a3\7g\2\2\u00a3\u00a4\7<\2\2"
	    + "\u00a4\64\3\2\2\2\u00a5\u00a6\7H\2\2\u00a6\66\3\2\2\2\u00a7\u00a8\7I\2"
	    + "\2\u00a8\u00a9\7n\2\2\u00a9\u00aa\7q\2\2\u00aa\u00ab\7d\2\2\u00ab\u00ac"
	    + "\7c\2\2\u00ac\u00ad\7n\2\2\u00ad\u00ae\7E\2\2\u00ae\u00af\7q\2\2\u00af"
	    + "\u00b0\7p\2\2\u00b0\u00b1\7f\2\2\u00b1\u00b2\7k\2\2\u00b2\u00b3\7v\2\2"
	    + "\u00b3\u00b4\7k\2\2\u00b4\u00b5\7q\2\2\u00b5\u00b6\7p\2\2\u00b6\u00b7"
	    + "\7u\2\2\u00b78\3\2\2\2\u00b8\u00b9\7+\2\2\u00b9:\3\2\2\2\u00ba\u00bc\t"
	    + "\2\2\2\u00bb\u00ba\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd\u00bb\3\2\2\2\u00bd"
	    + "\u00be\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c0\b\36\2\2\u00c0<\3\2\2\2"
	    + "\u00c1\u00c2\7\f\2\2\u00c2>\3\2\2\2\u00c3\u00c5\t\3\2\2\u00c4\u00c3\3"
	    + "\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7"
	    + "@\3\2\2\2\u00c8\u00ca\t\4\2\2\u00c9\u00c8\3\2\2\2\u00ca\u00cb\3\2\2\2"
	    + "\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00ccB\3\2\2\2\6\2\u00bd\u00c6" + "\u00cb\3\b\2\2";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
	_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
	    _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
	}
    }
}