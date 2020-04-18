package classes;
import java.sql.*;
import java.util.ArrayList;

public class BancoDeDados {
	private String db;
    private String driver;
    private String url;
    private String usuario;
    private String senha;
    private Connection conexao;
    private ResultSet resultado;

   /**
    * Construtor da classe
    * @param nomeDB o nome do Banco de dados para o qual deve ser preparado os dados de conexão
    * Aceita apenas os nomes: MYSQL, INTERBASE ou FIREBIRD
    * Dados como url, usuario e senha do banco não são recebido via parâmetro. Estão fixos neste
    * método.
    */
    public BancoDeDados(String nomeDB) throws ClassNotFoundException {
      if ( nomeDB.toUpperCase() == "MYSQL" )
      {
		db = nomeDB;
  		driver = "org.gjt.mm.mysql.Driver";
  		url="jdbc:mysql://localhost:3306/fatan2008";
 		usuario="root";
  		senha="root";
	   }
      if ( nomeDB.toUpperCase() == "FIREBIRD" || nomeDB.toUpperCase() == "INTERBASE" )
      {
		db = nomeDB;
  		driver = "org.firebirdsql.jdbc.FBDriver";
  		url="jdbc:firebirdsql:localhost/3050:D:\\firebird\\sistema.fdb";
 		usuario="SYSDBA";
  		senha="masterkey";
	   }
    }

   /**
    * Faz a conexão com o banco de dados, de acordo com o banco escolhido no construtor
    * @return retorno é um atributo booleano que será true se a conexão for bem sucessida
    * ou false em caso de falha
    */
    public boolean conecta() throws SQLException {
	      boolean retorno = true;
  	      try{
     	     Class.forName(driver);
    	     conexao = DriverManager.getConnection(url, usuario, senha);
	         }
	      catch(Exception ex) {
     	  	  System.err.println("Erro na conexao:\n " + ex.getMessage());
     	  	  retorno = false;
             }
          return retorno;
    }

   /**
    * Executa uma senteça sql apenas para consulta (comando select)
    * @param sql qualquer comando sql do banco de dados
    * @return resultado é um ResultSet (resultado) da execução do sql
    */
    public void consultar(String sql) throws SQLException {
  	   // monta e executa consulta
	   Statement s = conexao.createStatement();
	   resultado = s.executeQuery(sql);
    }

    /**
     * Executa uma senteça sql para inserir, alterar ou excluir (Insert, Update, Delete) 
     * @param sql qualquer comando sql do banco de dados
     * @return resultado é um ResultSet (resultado) da execução do sql
     */
     public void atualizar(String sql) throws SQLException {
   	   // monta e executa consulta
       int i=-1;
 	   Statement s = conexao.createStatement();
 	   i = s.executeUpdate(sql);
     }
    
    
   /**
    * Mostra a estrutura de uma tabela, considerando que tenha sido executado um sql
    * com o comando desc tabela-desejada
    * @return str é um buffer string com a descrição da tabela
    */
    public String estrutura() throws SQLException {
	  // obtem e transforma em String a estrutura da tabela
  	  StringBuffer str = new StringBuffer("[ ");
	  ResultSetMetaData m = resultado.getMetaData();
	  int colCount = m.getColumnCount();
	  for (int i=1; i<=colCount; ++i)
	      str.append(m.getColumnName(i) + " \t");
	  return str.append(" ]").toString();
    }

   /**
    * Retorna a quantidade de colunas, de acordo com o último comando sql executado
    * @return colCount quantidade de colunas da tabela
    */
    public int qtdCampos() throws SQLException {
	  // obtem e transforma em String a estrutura da tabela
	  // e devolve a quantidade de colunas que existe
	  ResultSetMetaData m = resultado.getMetaData();
	  int colCount = m.getColumnCount();
	  return colCount;
    }

    /**
     * Retorna a quantidade de colunas, de acordo com o último comando sql executado
     * @return colCount quantidade de colunas da tabela
     */
     public int qtdLinhas() throws SQLException {
 	  // somente deve ser acionado antes de utilizar os registros
 	  // e devolve a quantidade de colunas que existe
      resultado.last();
      int linCount = resultado.getRow();
      resultado.first();
 	  return linCount;
     }

    /**
    * Retorna true se encontrou algum registro no último comando sql
    * @return resultado verdadeiro ou false, caso exista ou não registros no ultimo resultado sql
    */
	public boolean TemRegistro() throws SQLException {
		// resultado contem em cada linha um registro lido pelo
		// sql que foi acionado. Na primeira linha tem-se
		// apenas os nomes dos atributos (colunas), a partir
		// da segunda tem-se os registros propriamente dito
		return(resultado.next());
	}

   /**
    * Retorna o conteúdo de um campo, de acordo com a posição numérica do mesmo
    * @param posicao numero da posicao do campo
    * @return str conteudo do campo
    */
	public String PegaCampo(int posicao) throws SQLException {
		// considera que o ponteiro ja esta no registro desejado
		StringBuffer str = new StringBuffer();
		str.append(resultado.getString(posicao));
		return str.toString();
	}

	   /**
	    * Retorna o conteúdo de um campo, de acordo com a posição numérica do mesmo
	    * @param posicao numero da posicao do campo
	    * @return str conteudo do campo
	    */
		public String PegaCampo(String nome) throws SQLException {
			// considera que o ponteiro ja esta no registro desejado
			StringBuffer str = new StringBuffer();
			str.append(resultado.getString(nome));
			return str.toString();
		}
   /**
    * Mostra o resultado do ultimo comando sql executado
    * @return str um buffer string com o resultado do ultimo comando sql
    */
    public String mostraTudo() throws SQLException {
	  // obtem e transforma em String o conteudo da tabela
	  StringBuffer str = new StringBuffer();
	  int colCount = resultado.getMetaData().getColumnCount();
	  while (resultado.next()) {
	      for (int i=1; i<=colCount; ++i)
	  	  str.append(resultado.getString(i) + " \t");
	      str.append("\n");
	}

	return str.toString();
    }

    public void close() throws SQLException {
	   conexao.close();
    }

}
