import com.heikkiv.ml.thomas.training.IrcTrainer

def input = new File('input_data.txt')
def trainer = new IrcTrainer()

trainer.train(input)

println 'DONE'
