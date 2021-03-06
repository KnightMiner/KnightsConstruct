package knightminer.tcomplement.library.steelworks;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.util.RecipeMatch;

public interface IMixRecipe extends IHighOvenFilter {
	/* Matches checks */

	/**
	 * Checks if this recipe matches the given input, this should uniquely identify each mix recipe.
	 * This method skips stack size and temperature checks, use {@link #canMix(FluidStack, ItemStack, ItemStack, ItemStack, int)}
	 * to determine if this recipe can be used.
	 *
	 * @param fluid     Input fluid
	 * @param oxidizer  Input oxidizer
	 * @param reducer   Input reducer
	 * @param purifier  Input purifier
	 * @return  true if the recipe matches, false otherwise
	 */
	boolean matches(FluidStack fluid, ItemStack oxidizer, ItemStack reducer, ItemStack purifier);

	/**
	 * Checks if this recipe can mix given the current state. This is called after the recipe is cached.
	 * This assumes {@link #matches(FluidStack, ItemStack, ItemStack, ItemStack)} returned true
	 *
	 * @param fluid       Input fluid stack, used to check additive sizes
	 * @param oxidizer    Input oxidizer, only the stack size is validated
	 * @param reducer     Input reducer, only the stack size is validated
	 * @param purifier    Input purifier, only the stack size is validated
	 * @param temperature Input temperature
	 * @return True if the recipe is valid under these sizes and temperatures
	 */
	default boolean canMix(FluidStack fluid, ItemStack oxidizer, ItemStack reducer, ItemStack purifier, int temperature) {
		return true;
	}


	/* Output methods */

	/**
	 * Applies the recipe to the input, returning the maximum amount
	 * @param input        Input fluid stack
	 * @param temperature  Current high oven temperature
	 * @return  FluidStack result
	 */
	FluidStack getOutput(FluidStack input, int temperature);

	/**
	 * Gets the normal output of this recipe
	 * @return  FluidStack output
	 */
	default FluidStack getOutput() {
		return null;
	}

	/**
	 * Updates the additives based on the result of the recipe
	 * @param fluid        Input fluid stack, so we know how many times it matched
	 * @param oxidizer     Input oxidizer, may be modified
	 * @param reducer      Input reducer, may be modified
	 * @param purifier     Input purifier, may be modified
	 * @param temperature  Current high oven temperature
	 */
	void updateAdditives(FluidStack fluid, ItemStack oxidizer, ItemStack reducer, ItemStack purifier, int temperature);


	/* Additive addition methods */

	/**
	 * Adds an additive to this recipe
	 *
	 * @param type     Type of the additive
	 * @param additive RecipeMatch entry, amountMatched is used as consumption chance
	 */
	void addAdditive(MixAdditive type, RecipeMatch additive);

	/**
	 * Adds an oxidizer to this recipe
	 *
	 * @param oxidizer  RecipeMatch entry, amountMatched is used as consumption chance
	 * @return  IMixRecipe instance for chaining
	 */
	default IMixRecipe addOxidizer(RecipeMatch oxidizer) {
		addAdditive(MixAdditive.OXIDIZER, oxidizer);
		return this;
	}

	/**
	 * Adds an oxidizer to this recipe
	 *
	 * @param stack    Oxidizer stack
	 * @param consume  Percent chance this oxidizer is consumed on this operation
	 * @return  IMixRecipe instance for chaining
	 */
	default IMixRecipe addOxidizer(ItemStack stack, int consume) {
		return addOxidizer(RecipeMatch.of(stack, consume));
	}

	/**
	 * Adds an oxidizer to this recipe
	 *
	 * @param oredict  Oxidizer oredict name
	 * @param consume  Percent chance this oxidizer is consumed on this operation
	 * @return  IMixRecipe instance for chaining
	 */
	default IMixRecipe addOxidizer(String oredict, int consume) {
		return addOxidizer(RecipeMatch.of(oredict, consume));
	}

	/**
	 * Adds an reducer to this recipe
	 *
	 * @param reducer  RecipeMatch entry, amountMatched is used as consumption chance
	 * @return  IMixRecipe instance for chaining
	 */
	default IMixRecipe addReducer(RecipeMatch reducer) {
		addAdditive(MixAdditive.REDUCER, reducer);
		return this;
	}

	/**
	 * Adds an reducer to this recipe
	 *
	 * @param stack    Reducer stack
	 * @param consume  Percent chance this reducer is consumed on this operation
	 * @return  IMixRecipe instance for chaining
	 */
	default IMixRecipe addReducer(ItemStack stack, int consume) {
		return addReducer(RecipeMatch.of(stack, consume));
	}

	/**
	 * Adds an reducer to this recipe
	 *
	 * @param oredict  Reducer oredict name
	 * @param consume  Percent chance this reducer is consumed on this operation
	 * @return  IMixRecipe instance for chaining
	 */
	default IMixRecipe addReducer(String oredict, int consume) {
		return addReducer(RecipeMatch.of(oredict, consume));
	}

	/**
	 * Adds an purifier to this recipe
	 *
	 * @param purifier  RecipeMatch entry, amountMatched is used as consumption chance
	 * @return  IMixRecipe instance for chaining
	 */
	default IMixRecipe addPurifier(RecipeMatch purifier) {
		addAdditive(MixAdditive.PURIFIER, purifier);
		return this;
	}

	/**
	 * Adds an purifier to this recipe
	 *
	 * @param stack    Purifier stack
	 * @param consume  Percent chance this purifier is consumed on this operation
	 * @return  IMixRecipe instance for chaining
	 */
	default IMixRecipe addPurifier(ItemStack stack, int consume) {
		return addPurifier(RecipeMatch.of(stack, consume));
	}

	/**
	 * Adds an purifier to this recipe
	 *
	 * @param oredict  Purifier oredict name
	 * @param consume  Percent chance this purifier is consumed on this operation
	 * @return  IMixRecipe instance for chaining
	 */
	default IMixRecipe addPurifier(String oredict, int consume) {
		return addPurifier(RecipeMatch.of(oredict, consume));
	}
}
