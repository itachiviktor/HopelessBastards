package screenconverter;
/*A Converter komponenscsomag biztos�t�s�t v�gzi.A ProviderFactory komponens ezen kereszt�l �ri el
 a ConverterProvider oszt�lyomat.*/
public interface IConverterProvider {
	public IConverter getConverter();
}
